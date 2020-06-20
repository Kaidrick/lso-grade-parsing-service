package moe.ofs.lsograde.mapper;

import moe.ofs.lsograde.model.LandingGrade;

import java.util.List;

public interface LandingGradeMapper {

    LandingGrade selectById(Long id);

    List<LandingGrade> selectAll();

    int insert(LandingGrade landingGrade);

    LandingGrade selectLandingGradeErrorItemById(Long id);
}
