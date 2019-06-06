package pt.saudemin.hds.entities.base;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pt.saudemin.hds.entities.User;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@EqualsAndHashCode
public abstract class Answer implements Serializable {

    @Id
    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Id
    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Getter
    @Setter
    @Column(nullable = false)
    private String observations;
}
