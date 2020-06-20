package moe.ofs.lsograde.services;

import moe.ofs.lsograde.config.GradeConfig;
import moe.ofs.lsograde.mapper.ErrorItemMapper;
import moe.ofs.lsograde.mapper.LandingGradeErrorItemMapper;
import moe.ofs.lsograde.mapper.LandingGradeMapper;
import moe.ofs.lsograde.model.ErrorItem;
import moe.ofs.lsograde.model.LandingGrade;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class GradeParsingServiceImpl implements GradeParsingService {

    private final GradeConfig config;

    private final SqlSession sqlSession;

    public GradeParsingServiceImpl(GradeConfig config, SqlSession sqlSession) {
        this.config = config;
        this.sqlSession = sqlSession;
    }

    @Override
    public LandingGrade parseRawGrade(String rawGrade) {

        if(!validate(rawGrade)) return null;

        LandingGrade.LandingGradeBuilder builder = LandingGrade.builder();

        List<ErrorItem> errorItemList = new ArrayList<>();

        builder
                .rawGrade(rawGrade)
                .carrierName("CVN-74")
                .aircraftType("Tomcat")
                .errorItems(errorItemList)
                .timestamp(LocalDateTime.now());

        Matcher gradeMatcher = Pattern.compile("(?<=GRADE:)\\S+(?=\\s)").matcher(rawGrade);
        if(gradeMatcher.find()) {
            builder.grade(gradeMatcher.group());
        }

        Matcher wireMatcher = Pattern.compile("(?<=WIRE#\\s)\\d?").matcher(rawGrade);
        if(wireMatcher.find()) {
            builder.wire(Integer.parseInt(wireMatcher.group()));
        }

        // sanitize the string
        List<String> list = Arrays.asList(
                rawGrade
                        .replaceAll("LSO:\\sGRADE:\\S+\\s:?", "")
                        .replaceAll("(WIRE#\\s\\d)", "").trim()
                        .split("\\s+"));

        // assume landingGrade items are unordered
        list.forEach(item -> analyzeGradeItems(errorItemList, item));


        LandingGrade landingGrade = builder.build();

        LandingGradeMapper landingGradeMapper = sqlSession.getMapper(LandingGradeMapper.class);
        ErrorItemMapper errorItemMapper = sqlSession.getMapper(ErrorItemMapper.class);
        LandingGradeErrorItemMapper landingGradeErrorItemMapper =
                sqlSession.getMapper(LandingGradeErrorItemMapper.class);

        landingGradeMapper.insert(landingGrade);
        errorItemMapper.insertList(errorItemList);
        landingGradeErrorItemMapper.insertListByLandingGradeId(landingGrade.getId(), errorItemList);

        sqlSession.commit();

        return landingGrade;
    }

    @Override
    public boolean validate(String rawGrade) {
        return rawGrade.startsWith("LSO: GRADE:");
    }

    private void analyzeGradeItems(List<ErrorItem> errorItemList, String item) {
        ErrorItem errorItem = new ErrorItem();

        // check for level
        if(item.contains("_")) {
            errorItem.setLevel("badly");
        } else if(item.contains("(")) {
            errorItem.setLevel("slightly");
        } else if(item.contains("[")) {
            errorItem.setLevel("no ball call");
        }

        for(String mark : config.getDistMark()) {
            if(item.contains(mark)) {
                errorItem.setPosition(mark);
                break;
            }
        }

        // check for error code with more than one letter
        // if found, flag errorMatch
        boolean errorMatch = false;
        for(String error : config.getError()) {
            if(item.contains(error)) {
                errorItem.setError(error);
                errorMatch = true;
                break;
            }
        }

        // if no match for multi-letter error code, match single letter error code
        if(!errorMatch) {
            for(String errorSingle : config.getErrorSingle()) {
                if(item.contains(errorSingle)) {
                    errorItem.setError(errorSingle);
                    break;
                }
            }
        }

        errorItemList.add(errorItem);
    }

}
