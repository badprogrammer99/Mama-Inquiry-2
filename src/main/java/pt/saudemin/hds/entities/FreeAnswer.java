package pt.saudemin.hds.entities;

import lombok.Getter;
import lombok.Setter;

import pt.saudemin.hds.entities.base.Answer;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class FreeAnswer extends Answer {

    @Getter
    @Setter
    @Column(nullable = false)
    private String answer;
}
