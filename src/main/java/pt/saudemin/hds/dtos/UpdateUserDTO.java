package pt.saudemin.hds.dtos;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
public class UpdateUserDTO extends UserDTO {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private static final long serialVersionUID = -4205209896882953571L;

    @NotNull(message = "The old personal id is required!")
    private Integer oldPersonalId;

    public UpdateUserDTO(Long id, Integer personalId, String name, Boolean isAdmin, List<InquiryDTO> inquiries, Integer oldPersonalId) {
        super(id, personalId, name, isAdmin, inquiries);
        this.oldPersonalId = oldPersonalId;
    }
}
