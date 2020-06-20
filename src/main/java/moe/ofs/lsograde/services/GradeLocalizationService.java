package moe.ofs.lsograde.services;

import moe.ofs.lsograde.model.LandingGrade;

public interface GradeLocalizationService {

    LandingGrade translate(LandingGrade landingGrade, String localeName);

}
