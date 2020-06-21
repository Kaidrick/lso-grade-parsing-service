package moe.ofs.lsograde.config;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class GradeConfig {

    private String[] error = {
            "AFU", "DL", "DR", "EG", "FD", "LL", "LR", "LUL", "LUR",
            "NERD", "NSU", "SLO", "TMRD", "LLWD", "LRWD", "LNF", "3PTS"
    };

    private String[] errorSingle = {
            "F", "H", "N", "W", "P", "LO", "B"
    };

    private String[] distMark = {
            "BC", "X", "IM", "IC", "AR", "TL", "IW", "AW"
    };
}
