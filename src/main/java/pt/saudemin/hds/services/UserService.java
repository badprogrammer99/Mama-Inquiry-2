package pt.saudemin.hds.services;

import pt.saudemin.hds.dtos.ChangePasswordDTO;
import pt.saudemin.hds.dtos.UpdateUserDTO;
import pt.saudemin.hds.dtos.entities.InquiryDTO;
import pt.saudemin.hds.dtos.entities.abstracts.AnswerDTO;
import pt.saudemin.hds.dtos.entities.abstracts.AnswerDTOList;
import pt.saudemin.hds.dtos.login.LoginDTO;
import pt.saudemin.hds.dtos.login.LoginInfoDTO;
import pt.saudemin.hds.dtos.entities.UserDTO;
import pt.saudemin.hds.exceptions.AttachingInquiriesToAdminException;
import pt.saudemin.hds.exceptions.ErraticAnswerInputException;
import pt.saudemin.hds.exceptions.NotAssociatedToInquiryException;
import pt.saudemin.hds.exceptions.PossibleAnswersExceededException;

import java.util.List;

public interface UserService {
    UserDTO getCurrentlyAuthenticatedUser();
    List<UserDTO> getAllUsers();
    UserDTO getByPersonalId(int id);
    List<InquiryDTO> getUserInformation(int id);
    UserDTO create(UserDTO userDTO) throws AttachingInquiriesToAdminException;
    UserDTO update(UpdateUserDTO updateUserDTO) throws AttachingInquiriesToAdminException;
    Boolean delete(int id);
    LoginInfoDTO authenticateUser(LoginDTO loginDTO);
    Boolean isIdDuplicate(int id);
    Boolean changeUserPassword(ChangePasswordDTO changePasswordDTO);
    Boolean setUserAnswersToQuestions(List<AnswerDTO> answers) throws PossibleAnswersExceededException,
            NotAssociatedToInquiryException, ErraticAnswerInputException;
    Boolean sendEmailWithUserDetails(long id);
}
