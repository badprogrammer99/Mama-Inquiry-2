package pt.saudemin.hds.dtos;

import java.io.Serializable;
import java.util.List;

import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Data
public class QuestionnaireDTO implements Serializable {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final static long serialVersionUID = 7717368858436448393L;

    private int id;
    private String name;
    private List<QuestionDTO> questions;

}
