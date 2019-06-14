package pt.saudemin.hds.dtos.entities;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerChoiceDTO implements Serializable {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private static final long serialVersionUID = 4302458742901058510L;

    private Long id;

    @NotBlank(message = "An answer choice name is required!")
    private String name;
}
