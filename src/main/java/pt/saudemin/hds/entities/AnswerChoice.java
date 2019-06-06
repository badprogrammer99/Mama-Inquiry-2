package pt.saudemin.hds.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class AnswerChoice {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_choice_id")
    private Long id;

    @Getter
    @Setter
    @Column(nullable = false)
    private String name;
}
