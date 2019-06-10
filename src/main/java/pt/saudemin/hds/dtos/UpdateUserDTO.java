package pt.saudemin.hds.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
public class UpdateUserDTO extends UserDTO {

    @NotNull(message = "The user past personal id is required!")
    private Integer oldPersonalId;

    public UpdateUserDTO(Long id, Integer personalId, String name, Boolean isAdmin, List<InquiryDTO> inquiries, Integer oldPersonalId) {
        super(id, personalId, name, isAdmin, inquiries);
        this.oldPersonalId = oldPersonalId;
    }
}
