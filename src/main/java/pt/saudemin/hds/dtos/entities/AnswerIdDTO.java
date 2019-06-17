package pt.saudemin.hds.dtos.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerIdDTO implements Serializable {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private static final long serialVersionUID = -6141892831459744998L;

    @NotBlank(message = "A question is required!")
    private QuestionDTO question;

    @JsonIgnore
    private UserDTO user;
}
