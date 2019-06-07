package pt.saudemin.hds.services;

import pt.saudemin.hds.dtos.LoginDTO;
import pt.saudemin.hds.dtos.LoginInfoDTO;
import pt.saudemin.hds.dtos.UserDTO;
import pt.saudemin.hds.entities.base.Answer;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();
    UserDTO getById(long id);
    UserDTO getByPersonalId(int id);
    LoginInfoDTO authenticateUser(LoginDTO loginDTO);
    UserDTO create(UserDTO user);
    Boolean checkForDuplicateIds(long id);
    Boolean setUserPassword(String oldPassword, String newPassword);
    Boolean sendEmailWithUserDetails(long id);
    Boolean setUserAnswersToQuestionnaire(List<Answer> answers);
}
