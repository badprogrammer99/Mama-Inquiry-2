package pt.saudemin.hds.entities;

import lombok.*;

import pt.saudemin.hds.entities.base.Question;

import javax.persistence.*;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ChoiceQuestion extends Question {

    @Column(name = "possible_answers")
    private Integer possibleAnswers;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "question_answer_choices",
        joinColumns = @JoinColumn(name = "question_id"),
        inverseJoinColumns = @JoinColumn(name = "answer_choice_id")
    )
    private Set<AnswerChoice> answerChoices;

    @Builder
    public ChoiceQuestion(Long id, String name, String description, Questionnaire questionnaire, Integer possibleAnswers, Set<AnswerChoice> answerChoices) {
        super(id, name, description, questionnaire);
        this.possibleAnswers = possibleAnswers;
        this.answerChoices = answerChoices;
    }
}
