package pt.saudemin.hds.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
public class QuestionDTO implements Serializable {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private static final long serialVersionUID = -1847115681816822014L;

    private Long id;
    private String name;
    private String description;
}
