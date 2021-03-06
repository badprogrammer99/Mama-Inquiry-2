package pt.saudemin.hds.dtos.entities;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ChoiceQuestionDTO extends QuestionDTO {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private static final long serialVersionUID = -3992117254967299922L;

    @NotNull(message = "A number of possible answers is required!")
    private Integer possibleAnswers;

    @NotBlank(message = "A list of possible answer choices is required!")
    private List<AnswerChoiceDTO> answerChoices;

    public ChoiceQuestionDTO(Long id, String name, String description, Integer possibleAnswers, List<AnswerChoiceDTO> answerChoices) {
        super(id, name, description);
        this.possibleAnswers = possibleAnswers;
        this.answerChoices = answerChoices;
    }
}
