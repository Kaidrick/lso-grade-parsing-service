package moe.ofs.lsograde.controllers;

import lombok.extern.slf4j.Slf4j;
import moe.ofs.lsograde.mapper.LandingGradeMapper;
import moe.ofs.lsograde.model.LandingGrade;
import moe.ofs.lsograde.services.GradeLocalizationService;
import moe.ofs.lsograde.services.GradeParsingService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("v1/lsograde")
public class LsoGradeServiceController {

    private final GradeParsingService parsingService;
    private final GradeLocalizationService localizationService;
    private final SqlSession sqlSession;

    public LsoGradeServiceController(GradeParsingService parsingService, GradeLocalizationService localizationService,
                                     SqlSession sqlSession) {
        this.parsingService = parsingService;
        this.localizationService = localizationService;
        this.sqlSession = sqlSession;
    }

    @RequestMapping(value = "/show/all", method = RequestMethod.GET)
    public List<LandingGrade> showAll() {

        LandingGradeMapper mapper = sqlSession.getMapper(LandingGradeMapper.class);
        return mapper.selectAll();
    }

    @RequestMapping(value = "/show/{id}", method = RequestMethod.GET)
    public LandingGrade show(@PathVariable("id") Long id) {
        LandingGradeMapper mapper = sqlSession.getMapper(LandingGradeMapper.class);
        return mapper.selectLandingGradeErrorItemById(id);
    }

    @RequestMapping(value = "/show/{id}/{locale}", method = RequestMethod.GET)
    public LandingGrade show(@PathVariable("id") Long id, @PathVariable("locale") String localeName) {
        // check if localeName is valid, otherwise use en_US
        LandingGradeMapper mapper = sqlSession.getMapper(LandingGradeMapper.class);

        return localizationService.translate(mapper.selectLandingGradeErrorItemById(id), localeName);
    }

    @RequestMapping(value = "/parse", method = RequestMethod.POST)
    public LandingGrade parseGrade(@RequestBody String rawGrade) {

        return parsingService.parseRawGrade(rawGrade);
    }

    @RequestMapping(value = "/parse/{locale}", method = RequestMethod.POST)
    public LandingGrade parseGrade(@RequestBody String rawGrade, @PathVariable("locale") String localeName) {

        return localizationService.translate(parsingService.parseRawGrade(rawGrade), localeName);
    }
}
