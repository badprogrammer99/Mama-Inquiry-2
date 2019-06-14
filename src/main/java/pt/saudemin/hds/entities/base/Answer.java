package pt.saudemin.hds.entities.base;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Answer {

    @EmbeddedId
    private AnswerId answerId;

    @Column
    private String observations;
}
