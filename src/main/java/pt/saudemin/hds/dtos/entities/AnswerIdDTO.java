package pt.saudemin.hds.dtos.entities;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerIdDTO implements Serializable {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private static final long serialVersionUID = -6141892831459744998L;

    private QuestionDTO question;
    private UserDTO user;
}
