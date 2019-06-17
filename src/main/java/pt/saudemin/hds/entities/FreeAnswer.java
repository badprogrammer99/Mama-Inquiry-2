package pt.saudemin.hds.entities;

import lombok.*;

import pt.saudemin.hds.entities.base.Answer;
import pt.saudemin.hds.entities.base.AnswerId;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class FreeAnswer extends Answer {

    @Column
    private String answer;

    public FreeAnswer(AnswerId answerId, String observations, String answer) {
        super(answerId, observations);
        this.answer = answer;
    }
}
