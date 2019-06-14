package pt.saudemin.hds.services;

import pt.saudemin.hds.dtos.ChangePasswordDTO;
import pt.saudemin.hds.dtos.UpdateUserDTO;
import pt.saudemin.hds.dtos.entities.abstracts.AnswerDTO;
import pt.saudemin.hds.dtos.login.LoginDTO;
import pt.saudemin.hds.dtos.login.LoginInfoDTO;
import pt.saudemin.hds.dtos.entities.UserDTO;
import pt.saudemin.hds.entities.base.Answer;
import pt.saudemin.hds.exceptions.AttachingInquiriesToAdminException;
import pt.saudemin.hds.exceptions.GivenAnswersExceedQuestionPossibleAnswersException;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();
    UserDTO getByPersonalId(int id);
    UserDTO create(UserDTO userDTO) throws AttachingInquiriesToAdminException;
    UserDTO update(UpdateUserDTO updateUserDTO) throws AttachingInquiriesToAdminException;
    Boolean delete(int id);
    LoginInfoDTO authenticateUser(LoginDTO loginDTO);
    Boolean isIdDuplicate(long id);
    Boolean setUserPassword(ChangePasswordDTO changePasswordDTO);
    Boolean setUserAnswersToQuestions(List<AnswerDTO> answers) throws GivenAnswersExceedQuestionPossibleAnswersException;
    Boolean sendEmailWithUserDetails(long id);
}
