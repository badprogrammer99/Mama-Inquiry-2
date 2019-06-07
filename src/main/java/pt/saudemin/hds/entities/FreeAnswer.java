package pt.saudemin.hds.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import pt.saudemin.hds.entities.base.Answer;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class FreeAnswer extends Answer {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private static final long serialVersionUID = 6509025077028473497L;

    @Column(nullable = false)
    private String answer;
}
