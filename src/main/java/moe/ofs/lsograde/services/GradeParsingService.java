package moe.ofs.lsograde.services;

import moe.ofs.lsograde.model.LandingGrade;

public interface GradeParsingService {

    LandingGrade parseRawGrade(String rawGrade);

    boolean validate(String rawGrade);

}
