package moe.ofs.lsograde.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorItem implements Serializable {

    private static final Long serialVersionUID = 1L;

    @JsonIgnore
    private Long id;

    private String level;

    private String position;

    private String error;

}
