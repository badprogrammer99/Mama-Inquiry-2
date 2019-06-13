package pt.saudemin.hds.entities;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import pt.saudemin.hds.entities.base.Answer;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ChoiceAnswer extends Answer {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private static final long serialVersionUID = 1067854538401314765L;

    @ManyToOne
    @JoinColumn(name = "answer_choice_id", nullable = false)
    private AnswerChoice answerChoice;
}
