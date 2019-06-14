package pt.saudemin.hds.services.impl;

import lombok.extern.slf4j.Slf4j;
import lombok.var;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import pt.saudemin.hds.dtos.ChangePasswordDTO;
import pt.saudemin.hds.dtos.UpdateUserDTO;
import pt.saudemin.hds.dtos.entities.ChoiceAnswerDTO;
import pt.saudemin.hds.dtos.entities.ChoiceQuestionDTO;
import pt.saudemin.hds.dtos.entities.FreeAnswerDTO;
import pt.saudemin.hds.dtos.entities.abstracts.AnswerDTO;
import pt.saudemin.hds.dtos.login.LoginDTO;
import pt.saudemin.hds.dtos.login.LoginInfoDTO;
import pt.saudemin.hds.dtos.entities.UserDTO;
import pt.saudemin.hds.entities.ChoiceAnswer;
import pt.saudemin.hds.entities.ChoiceQuestion;
import pt.saudemin.hds.entities.FreeAnswer;
import pt.saudemin.hds.entities.Inquiry;
import pt.saudemin.hds.entities.base.Answer;
import pt.saudemin.hds.exceptions.AttachingInquiriesToAdminException;
import pt.saudemin.hds.exceptions.GivenAnswersExceedQuestionPossibleAnswersException;
import pt.saudemin.hds.mappers.AnswerMapper;
import pt.saudemin.hds.mappers.ChoiceAnswerMapper;
import pt.saudemin.hds.mappers.FreeAnswerMapper;
import pt.saudemin.hds.mappers.UserMapper;
import pt.saudemin.hds.repositories.AnswerRepository;
import pt.saudemin.hds.repositories.UserRepository;
import pt.saudemin.hds.services.UserService;
import pt.saudemin.hds.utils.TokenUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnswerRepository<FreeAnswer> freeAnswerRepository;

    @Autowired
    private AnswerRepository<ChoiceAnswer> choiceAnswerRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper.INSTANCE::userToUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getByPersonalId(int id) {
        var user = userRepository.findByPersonalId(id);

        return user.map(UserMapper.INSTANCE::userToUserDTO).orElse(null);
    }

    @Override
    @Transactional
    public UserDTO create(UserDTO userDTO) throws AttachingInquiriesToAdminException {
        if (userDTO.getIsAdmin() && userDTO.getInquiries() != null) throw new AttachingInquiriesToAdminException();

        var user = UserMapper.INSTANCE.userDTOToUser(userDTO);
        user.setPassword(bCryptPasswordEncoder.encode(RandomStringUtils.random(10, true, true)));
        return UserMapper.INSTANCE.userToUserDTO(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserDTO update(UpdateUserDTO updateUserDTO) throws AttachingInquiriesToAdminException {
        var userByPersonalId = userRepository.findByPersonalId(updateUserDTO.getOldPersonalId());
        var userEntity = UserMapper.INSTANCE.userDTOToUser(updateUserDTO);
        UserDTO updatedUserDTO;

        if (userByPersonalId.isPresent()) {
            if (updateUserDTO.getIsAdmin() && updateUserDTO.getInquiries() != null)
                throw new AttachingInquiriesToAdminException();
            userEntity.setId(userByPersonalId.get().getId());
            userEntity.setPassword(userByPersonalId.get().getPassword());
            updatedUserDTO = UserMapper.INSTANCE.userToUserDTO(userRepository.save(userEntity));
        } else {
            return null;
        }

        return updatedUserDTO;
    }

    @Override
    public Boolean delete(int personalId) {
        try {
            userRepository.deleteByPersonalId(personalId);
        } catch (Exception e) {
            log.error("Error deleting object of type " + Inquiry.class.getSimpleName() + ". Exception details: " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public LoginInfoDTO authenticateUser(LoginDTO loginDTO) {
        var user = userRepository.findByPersonalId(loginDTO.getPersonalId());

        if (user.isPresent() && bCryptPasswordEncoder.matches(loginDTO.getPassword(), user.get().getPassword())) {
            return new LoginInfoDTO(TokenUtils.generateToken(user.get()), user.get().getPersonalId(), user.get().getIsAdmin());
        }

        return null;
    }

    @Override
    public Boolean isIdDuplicate(long id) {
        return userRepository.findById(id).isPresent();
    }

    @Override
    public Boolean setUserPassword(ChangePasswordDTO changePasswordDTO) {
        var user = userRepository.findByPersonalId(changePasswordDTO.getPersonalId());

        if (user.isPresent() && bCryptPasswordEncoder.matches(changePasswordDTO.getOldPassword(), user.get().getPassword())) {
            user.get().setPassword(bCryptPasswordEncoder.encode(changePasswordDTO.getNewPassword()));
            userRepository.save(user.get());
            return true;
        }

        return false;
    }

    @Override
    public Boolean setUserAnswersToQuestions(List<AnswerDTO> answers) throws GivenAnswersExceedQuestionPossibleAnswersException {
        for (AnswerDTO answer : answers) {
            if (answer instanceof ChoiceAnswerDTO) {
                var choiceAnswer = (ChoiceAnswerDTO) answer;
                var associatedQuestionPossibleAnswers = ((ChoiceQuestionDTO) choiceAnswer.getAnswerId().getQuestion()).getPossibleAnswers();

                if (choiceAnswer.getAnswerChoices().size() > associatedQuestionPossibleAnswers) {
                    throw new GivenAnswersExceedQuestionPossibleAnswersException();
                }
            }

            try {
                if (answer instanceof FreeAnswerDTO) {
                    freeAnswerRepository.save(FreeAnswerMapper.INSTANCE.freeAnswerDTOToFreeAnswer((FreeAnswerDTO) answer));
                } else if (answer instanceof ChoiceAnswerDTO) {
                    choiceAnswerRepository.save(ChoiceAnswerMapper.INSTANCE.choiceAnswerDTOToChoiceAnswer((ChoiceAnswerDTO) answer));
                }
            } catch (Exception e) {
                log.error("Error saving answers! Exception details: " + e.getMessage());
                return false;
            }
        }

        return true;
    }

    @Override
    public Boolean sendEmailWithUserDetails(long id) {
        throw new UnsupportedOperationException("Operation not supported yet.");
    }
}
