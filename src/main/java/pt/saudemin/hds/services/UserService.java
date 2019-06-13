package pt.saudemin.hds.services;

import pt.saudemin.hds.dtos.ChangePasswordDTO;
import pt.saudemin.hds.dtos.UpdateUserDTO;
import pt.saudemin.hds.dtos.entities.abstracts.AnswerDTO;
import pt.saudemin.hds.dtos.login.LoginDTO;
import pt.saudemin.hds.dtos.login.LoginInfoDTO;
import pt.saudemin.hds.dtos.entities.UserDTO;
import pt.saudemin.hds.entities.base.Answer;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();
    UserDTO getByPersonalId(int id);
    UserDTO create(UserDTO userDTO);
    UserDTO update(UpdateUserDTO updateUserDTO);
    Boolean delete(int id);
    LoginInfoDTO authenticateUser(LoginDTO loginDTO);
    Boolean isIdDuplicate(long id);
    Boolean setUserPassword(ChangePasswordDTO changePasswordDTO);
    Boolean setUserAnswersToQuestionnaire(List<AnswerDTO> answers);
    Boolean sendEmailWithUserDetails(long id);
}
