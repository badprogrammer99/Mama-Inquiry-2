package pt.saudemin.hds.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class QuestionDTO implements Serializable {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private static final long serialVersionUID = -1847115681816822014L;

    private Long id;

    @NotBlank(message = "A name is required!")
    private String name;

    @NotBlank(message = "A description is required!")
    private String description;
}
