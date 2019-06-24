package pt.saudemin.hds.dtos.entities;

import lombok.*;

import pt.saudemin.hds.dtos.entities.abstracts.AnswerDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ChoiceAnswerDTO extends AnswerDTO {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private static final long serialVersionUID = 5862738007419435140L;

    @NotNull(message = "Chosen answers are required!")
    private List<AnswerChoiceDTO> answerChoices;

    public ChoiceAnswerDTO(AnswerIdDTO answerId, String observations, List<AnswerChoiceDTO> answerChoices) {
        super(answerId, observations);
        this.answerChoices = answerChoices;
    }
}
