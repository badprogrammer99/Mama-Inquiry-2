package pt.saudemin.hds.dtos.entities.abstracts;

import lombok.*;
import pt.saudemin.hds.dtos.entities.AnswerIdDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AnswerDTO implements Serializable {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private static final long serialVersionUID = 8690772590470745416L;

    @NotBlank(message = "A composite ID is required!")
    private AnswerIdDTO answerId;

    private String observations;
}
