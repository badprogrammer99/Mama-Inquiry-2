package pt.saudemin.hds.entities;

import lombok.Getter;
import lombok.Setter;

import pt.saudemin.hds.entities.base.Answer;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ChoiceAnswer extends Answer {

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "answer_choice_id", nullable = false)
    private AnswerChoice answerChoice;
}
