package pt.saudemin.hds.entities.base;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Answer {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private static final long serialVersionUID = 7133254567050645241L;

    @EmbeddedId
    private AnswerId answerId;

    @Column(nullable = false)
    private String observations;
}
