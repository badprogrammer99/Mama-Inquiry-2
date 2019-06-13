package pt.saudemin.hds.dtos.entities;

import lombok.*;

import pt.saudemin.hds.dtos.entities.abstracts.AnswerDTO;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ChoiceAnswerDTO extends AnswerDTO {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private static final long serialVersionUID = 5862738007419435140L;

    private AnswerChoiceDTO answerChoice;

    public ChoiceAnswerDTO(AnswerIdDTO answerId, String observations, AnswerChoiceDTO answerChoiceDTO) {
        super(answerId, observations);
        this.answerChoice = answerChoiceDTO;
    }
}
