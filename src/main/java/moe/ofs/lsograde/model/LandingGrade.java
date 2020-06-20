package moe.ofs.lsograde.model;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LandingGrade implements Serializable {

    private static final Long serialVersionUID = 1L;

    private Long id;

    private LocalDateTime timestamp;

    private String rawGrade;

    private String carrierName;

    private String aircraftType;


    // analysis
    private String grade;

    private Integer wire;

    private List<ErrorItem> errorItems;
}
