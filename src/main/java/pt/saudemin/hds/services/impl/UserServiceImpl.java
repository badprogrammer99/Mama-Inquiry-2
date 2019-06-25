package pt.saudemin.hds.services.impl;

import lombok.extern.slf4j.Slf4j;
import lombok.var;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import pt.saudemin.hds.dtos.ChangePasswordDTO;
import pt.saudemin.hds.dtos.UpdateUserDTO;
import pt.saudemin.hds.dtos.entities.*;
import pt.saudemin.hds.dtos.entities.abstracts.AnswerDTO;
import pt.saudemin.hds.dtos.entities.abstracts.AnswerDTOList;
import pt.saudemin.hds.dtos.login.LoginDTO;
import pt.saudemin.hds.dtos.login.LoginInfoDTO;
import pt.saudemin.hds.entities.ChoiceQuestion;
import pt.saudemin.hds.entities.Inquiry;
import pt.saudemin.hds.entities.base.Question;
import pt.saudemin.hds.exceptions.AttachingInquiriesToAdminException;
import pt.saudemin.hds.exceptions.ErraticAnswerInputException;
import pt.saudemin.hds.exceptions.NotAssociatedToInquiryException;
import pt.saudemin.hds.exceptions.PossibleAnswersExceededException;
import pt.saudemin.hds.exceptions.base.AbstractSetUserAnswersException;
import pt.saudemin.hds.mappers.ChoiceAnswerMapper;
import pt.saudemin.hds.mappers.FreeAnswerMapper;
import pt.saudemin.hds.mappers.UserMapper;
import pt.saudemin.hds.repositories.AnswerRepository;
import pt.saudemin.hds.repositories.QuestionRepository;
import pt.saudemin.hds.repositories.UserRepository;
import pt.saudemin.hds.services.UserService;
import pt.saudemin.hds.utils.TokenUtils;

import javax.transaction.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private Subject subject;

    @SuppressWarnings("serial")
    private static final Map<String, String> ANSWER_QUESTION_MAPPING = new HashMap<String, String>() {{
        put(FreeAnswerDTO.class.getSimpleName(), Question.class.getSimpleName());
        put(ChoiceAnswerDTO.class.getSimpleName(), ChoiceQuestion.class.getSimpleName());
    }};

    @Override
    public UserDTO getCurrentlyAuthenticatedUser() {
        return getByPersonalId(Integer.valueOf((String) subject.getPrincipal()));
    }

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
    public List<InquiryDTO> getUserInformation(int id) {
        return getByPersonalId(id).getInquiries();
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
    @Transactional
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
    public Boolean isIdDuplicate(int id) {
        return userRepository.findByPersonalId(id).isPresent();
    }

    @Override
    @Transactional
    public Boolean changeUserPassword(ChangePasswordDTO changePasswordDTO) {
        var user = userRepository.findByPersonalId(changePasswordDTO.getPersonalId());

        if (user.isPresent() && bCryptPasswordEncoder.matches(changePasswordDTO.getOldPassword(), user.get().getPassword())) {
            user.get().setPassword(bCryptPasswordEncoder.encode(changePasswordDTO.getNewPassword()));
            userRepository.save(user.get());
            return true;
        }

        return false;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Boolean setUserAnswersToQuestions(List<AnswerDTO> answers) throws PossibleAnswersExceededException,
            NotAssociatedToInquiryException, ErraticAnswerInputException {

        for (AnswerDTO answer : answers) {
            Question associatedQuestionEntity;

            if (questionRepository.findById(answer.getAnswerId().getQuestion().getId()).isPresent()) {
                associatedQuestionEntity = questionRepository.findById(answer.getAnswerId().getQuestion().getId()).get();
            } else {
                throw new ErraticAnswerInputException("The question with the ID of " +
                        answer.getAnswerId().getQuestion().getId() + " doesn't exist.");
            }

            var respectiveQuestionEntityType = ANSWER_QUESTION_MAPPING.get(answer.getClass().getSimpleName());

            if (!associatedQuestionEntity.getClass().getSimpleName().equals(respectiveQuestionEntityType)) {
                throw new ErraticAnswerInputException("User is trying to provide an illegal answer to the question ID of " +
                        associatedQuestionEntity.getId() + ".");
            }

            var associatedInquiry = associatedQuestionEntity.getQuestionnaire().getInquiry();

            // Prevent users from answering questions in inquiries they are not associated with.
            if (getCurrentlyAuthenticatedUser().getInquiries().stream().noneMatch(inquiry -> inquiry.getId().equals(associatedInquiry.getId()))) {
                String msg = "The user with the personal ID of "
                        + getCurrentlyAuthenticatedUser().getPersonalId() + ", who is associated with the inquiries "
                        + getCurrentlyAuthenticatedUser().getInquiries()
                            .stream()
                            .map(InquiryDTO::getName)
                            .collect(Collectors.joining(", "))
                        + " tried to answer questions of the inquiry "
                        + associatedInquiry.getName() + ". This is considered a"
                        + " security violation and so, the transaction of the user answers"
                        + " cannot continue.";

                log.warn(msg);
                throw new NotAssociatedToInquiryException(msg);
            }

            if (answer instanceof ChoiceAnswerDTO) {
                var choiceAnswer = (ChoiceAnswerDTO) answer;
                var associatedQuestionPossibleAnswers = ((ChoiceQuestion) associatedQuestionEntity).getPossibleAnswers();

                // Prevent users from answering more times than the specified limit.
                if (choiceAnswer.getAnswerChoices().size() > associatedQuestionPossibleAnswers) {
                    throw new PossibleAnswersExceededException("User answered " + choiceAnswer.getAnswerChoices().size()
                            + " times to a question which only allows " + associatedQuestionPossibleAnswers + " possible answers!");
                }
            }

            answer.getAnswerId().setUser(getCurrentlyAuthenticatedUser());

            if (answer instanceof FreeAnswerDTO) {
                answerRepository.save(FreeAnswerMapper.INSTANCE.freeAnswerDTOToFreeAnswer((FreeAnswerDTO) answer));
            } else if (answer instanceof ChoiceAnswerDTO) {
                answerRepository.save(ChoiceAnswerMapper.INSTANCE.choiceAnswerDTOToChoiceAnswer((ChoiceAnswerDTO) answer));
            }
        }

        return true;
    }

    @Override
    public Boolean sendEmailWithUserDetails(long id) {
        throw new UnsupportedOperationException("Operation not supported yet.");
    }
}