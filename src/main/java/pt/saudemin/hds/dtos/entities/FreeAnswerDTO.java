package pt.saudemin.hds.dtos.entities;

import lombok.*;

import pt.saudemin.hds.dtos.entities.abstracts.AnswerDTO;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class FreeAnswerDTO extends AnswerDTO {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private static final long serialVersionUID = 5285513505867950123L;

    private String answer;

    public FreeAnswerDTO(AnswerIdDTO answerId, String observations, String answer) {
        super(answerId, observations);
        this.answer = answer;
    }
}
