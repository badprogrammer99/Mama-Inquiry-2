package pt.saudemin.hds.entities;

import lombok.*;

import pt.saudemin.hds.entities.base.Answer;
import pt.saudemin.hds.entities.base.AnswerId;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ChoiceAnswer extends Answer {

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "question_id"),
        @JoinColumn(name = "user_id")
    })
    private Set<AnswerChoice> answerChoices;

    public ChoiceAnswer(AnswerId answerId, String observations, Set<AnswerChoice> answerChoices) {
        super(answerId, observations);
        this.answerChoices = answerChoices;
    }
}
