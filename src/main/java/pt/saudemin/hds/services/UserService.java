package pt.saudemin.hds.services;

import pt.saudemin.hds.dtos.UpdateUserDTO;
import pt.saudemin.hds.dtos.login.LoginDTO;
import pt.saudemin.hds.dtos.login.LoginInfoDTO;
import pt.saudemin.hds.dtos.UserDTO;
import pt.saudemin.hds.entities.base.Answer;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();
    UserDTO getByPersonalId(int id);
    UserDTO create(UserDTO userDTO);
    UserDTO update(UpdateUserDTO updateUserDTO);
    Boolean delete(int id);
    LoginInfoDTO authenticateUser(LoginDTO loginDTO);
    Boolean checkForDuplicateIds(long id);
    Boolean setUserPassword(int personalId, String oldPassword, String newPassword);
    Boolean sendEmailWithUserDetails(long id);
    Boolean setUserAnswersToQuestionnaire(List<Answer> answers);
}
