package pt.saudemin.hds.services;

import pt.saudemin.hds.dtos.UserDTO;
import pt.saudemin.hds.entities.base.Answer;

import java.util.List;

public interface UserService {
    UserDTO getById(long id);
    UserDTO createNew(UserDTO user);
    Boolean checkForDuplicateIds(long id);
    Boolean setUserPassword(String oldPassword, String newPassword);
    Boolean sendEmailWithUserDetails(long id);
    Boolean setUserAnswersToQuestionnaire(List<Answer> answers);
}
