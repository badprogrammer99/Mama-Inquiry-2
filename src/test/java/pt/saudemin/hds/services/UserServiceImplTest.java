package pt.saudemin.hds.services;

import io.jsonwebtoken.Claims;
import lombok.var;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import pt.saudemin.hds.dtos.ChangePasswordDTO;
import pt.saudemin.hds.dtos.entities.*;
import pt.saudemin.hds.dtos.UpdateUserDTO;
import pt.saudemin.hds.dtos.entities.abstracts.AnswerDTO;
import pt.saudemin.hds.dtos.login.LoginDTO;
import pt.saudemin.hds.entities.base.AnswerId;
import pt.saudemin.hds.exceptions.AttachingInquiriesToAdminException;
import pt.saudemin.hds.exceptions.NotAssociatedToInquiryException;
import pt.saudemin.hds.exceptions.PossibleAnswersExceededException;
import pt.saudemin.hds.mappers.AnswerChoiceMapper;
import pt.saudemin.hds.mappers.QuestionMapper;
import pt.saudemin.hds.mappers.UserMapper;
import pt.saudemin.hds.repositories.AnswerChoiceRepository;
import pt.saudemin.hds.repositories.AnswerRepository;
import pt.saudemin.hds.repositories.UserRepository;
import pt.saudemin.hds.utils.TokenUtils;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceImplTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnswerChoiceRepository answerChoiceRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void testGetUserById() {
        var personalId = 170100228;

        var user = userService.getByPersonalId(personalId);

        Assert.assertNotNull(user);
        Assert.assertEquals((long) personalId, (long) user.getPersonalId());
    }

    @Test
    public void testCreateUser() throws AttachingInquiriesToAdminException {
        var userDTO = new UserDTO(null, 170100312, "Jajaja xd", false, new ArrayList<InquiryDTO>() {{
            new InquiryDTO(1L, null, null);
            new InquiryDTO(2L, null, null);
        }});

        var createdUser = userService.create(userDTO);
        var getCreatedUser = userService.getByPersonalId(170100312);

        Assert.assertNotNull(createdUser);
        Assert.assertEquals(userDTO.getPersonalId(), createdUser.getPersonalId());
        Assert.assertEquals(userDTO.getName(), createdUser.getName());
        Assert.assertEquals(userDTO.getIsAdmin(), createdUser.getIsAdmin());
        Assert.assertEquals(userDTO.getInquiries(), createdUser.getInquiries());

        Assert.assertNotNull(getCreatedUser);
        Assert.assertEquals(userDTO.getPersonalId(), getCreatedUser.getPersonalId());
        Assert.assertEquals(userDTO.getName(), getCreatedUser.getName());
        Assert.assertEquals(userDTO.getIsAdmin(), getCreatedUser.getIsAdmin());
        Assert.assertEquals(userDTO.getInquiries(), getCreatedUser.getInquiries());
    }

    @Test
    public void testUpdateUser() throws AttachingInquiriesToAdminException {
        var userDTO = new UpdateUserDTO(null,  170100481, "Jajaers xd", false, new ArrayList<InquiryDTO>() {{
            new InquiryDTO(2L, null, null);
        }}, 170100231);

        var updatedUser = userService.update(userDTO);
        var getUpdatedUser = userService.getByPersonalId(170100481);

        Assert.assertNotNull(updatedUser);
        Assert.assertEquals(userDTO.getPersonalId(), updatedUser.getPersonalId());
        Assert.assertEquals(userDTO.getName(), updatedUser.getName());
        Assert.assertEquals(userDTO.getIsAdmin(), updatedUser.getIsAdmin());
        Assert.assertEquals(userDTO.getInquiries(), updatedUser.getInquiries());

        Assert.assertNotNull(getUpdatedUser);
        Assert.assertEquals(userDTO.getPersonalId(), getUpdatedUser.getPersonalId());
        Assert.assertEquals(userDTO.getName(), getUpdatedUser.getName());
        Assert.assertEquals(userDTO.getIsAdmin(), getUpdatedUser.getIsAdmin());
        Assert.assertEquals(userDTO.getInquiries(), getUpdatedUser.getInquiries());
    }

    @Test
    public void testDeleteUser() {
        var id = 170100228;

        Assert.assertTrue(userService.delete(id));
        Assert.assertNull(userService.getByPersonalId(id));
    }

    @Test
    public void testLoginUser() {
        var loginDTO = new LoginDTO(170100228, "123456");
        var loginInfoDTO = userService.authenticateUser(loginDTO);

        Assert.assertNotNull(loginInfoDTO);

        Claims tokenClaims = TokenUtils.getTokenClaims(loginInfoDTO.getToken());

        Assert.assertEquals(loginDTO.getPersonalId().toString(), tokenClaims.getSubject());
        Assert.assertNotNull(tokenClaims.get("role").toString().equals("admin")
            ? "admin"
            : (tokenClaims.get("role").toString().equals("user")
                ? "user"
                : null)
        );
        Assert.assertEquals(loginInfoDTO.getPersonalId(), loginDTO.getPersonalId());
        Assert.assertEquals(loginInfoDTO.getIsAdmin(), true);
    }

    @Test
    public void testCheckDuplicateIds() {
        Assert.assertTrue(userService.isIdDuplicate(2));
    }

    @Test
    public void testChangePassword() {
        var changePasswordDTO = new ChangePasswordDTO(170100228, "123456", "abcdef");
        Assert.assertTrue(userService.changeUserPassword(changePasswordDTO));

        var user = userRepository.findByPersonalId(changePasswordDTO.getPersonalId());

        Assert.assertTrue(user.isPresent());
        Assert.assertTrue(bCryptPasswordEncoder.matches(changePasswordDTO.getNewPassword(), user.get().getPassword()));
    }

    @Test
    public void testSetAnswers() throws PossibleAnswersExceededException, NotAssociatedToInquiryException {
        var genericQuestions = questionService.getAllGenericQuestions();
        var choiceQuestions = questionService.getAllChoiceQuestions();

        var userDTO = userService.getByPersonalId(170100231);
        var user = UserMapper.INSTANCE.userDTOToUser(userDTO);

        List<AnswerDTO> answers = new ArrayList<AnswerDTO>(){{
            add(new FreeAnswerDTO(new AnswerIdDTO(genericQuestions.get(0), userDTO), null, "Atm XD"));
            add(new FreeAnswerDTO(new AnswerIdDTO(genericQuestions.get(1), userDTO), null, "Atm XD"));
            add(new ChoiceAnswerDTO(new AnswerIdDTO(choiceQuestions.get(0), userDTO), "Atm XD", new ArrayList<AnswerChoiceDTO>(){{
                add(AnswerChoiceMapper.INSTANCE.answerChoiceToAnswerChoiceDTO(answerChoiceRepository.findById(1L).get()));
                add(AnswerChoiceMapper.INSTANCE.answerChoiceToAnswerChoiceDTO(answerChoiceRepository.findById(2L).get()));
            }}));
        }};

        Assert.assertTrue(userService.setUserAnswersToQuestions(answers));

        var firstAnswer = answerRepository.findById(new AnswerId(QuestionMapper.INSTANCE.questionDTOToQuestion(genericQuestions.get(0)), user));
        var secondAnswer = answerRepository.findById(new AnswerId(QuestionMapper.INSTANCE.questionDTOToQuestion(genericQuestions.get(1)), user));
        var thirdAnswer = answerRepository.findById(new AnswerId(QuestionMapper.INSTANCE.questionDTOToQuestion(choiceQuestions.get(0)), user));

        Assert.assertTrue(firstAnswer.isPresent());
        Assert.assertTrue(secondAnswer.isPresent());
        Assert.assertTrue(thirdAnswer.isPresent());
    }
}
