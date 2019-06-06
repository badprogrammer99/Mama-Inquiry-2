package pt.saudemin.hds.mappers;

import org.mapstruct.Mapper;

import pt.saudemin.hds.dtos.UserDTO;
import pt.saudemin.hds.entities.User;

@Mapper(uses = {InquiryMapper.class, QuestionnaireMapper.class})
public interface UserMapper {

    UserDTO userToUserDTO(User user);
    User userDTOToUser(UserDTO userDTO);

}
