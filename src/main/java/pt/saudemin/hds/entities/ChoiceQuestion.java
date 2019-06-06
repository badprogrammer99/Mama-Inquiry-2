package pt.saudemin.hds.entities;

import lombok.Getter;
import lombok.Setter;
import pt.saudemin.hds.entities.base.Question;

import javax.persistence.*;
import java.util.Set;

@Entity
public class ChoiceQuestion extends Question {

    @Getter
    @Setter
    @Column(name = "possible_answers", nullable = false)
    private Integer possibleAnswers;

    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "question_answer_choices",
        joinColumns = @JoinColumn(name = "answer_choice_id"),
        inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    private Set<AnswerChoice> answerChoices;
}
