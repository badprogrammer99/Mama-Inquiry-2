package pt.saudemin.hds.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import org.mapstruct.factory.Mappers;
import pt.saudemin.hds.dtos.entities.UserDTO;
import pt.saudemin.hds.entities.User;

@Mapper(uses = {InquiryMapper.class, QuestionnaireMapper.class})
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserDTO userToUserDTO(User user);

    @Mapping(ignore = true, target = "password")
    User userDTOToUser(UserDTO userDTO);

}
