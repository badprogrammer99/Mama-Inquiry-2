package pt.saudemin.hds.dtos;

import java.io.Serializable;
import java.util.List;

import lombok.*;

@Data
public class QuestionnaireDTO implements Serializable {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final static long serialVersionUID = 7717368858436448393L;

    private Long id;
    private String name;

    private List<QuestionDTO> questions;
}
