package pt.saudemin.hds.services;

import io.jsonwebtoken.Claims;
import lombok.var;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.Before;
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
import pt.saudemin.hds.entities.ChoiceAnswer;
import pt.saudemin.hds.entities.FreeAnswer;
import pt.saudemin.hds.entities.Inquiry;
import pt.saudemin.hds.entities.base.AnswerId;
import pt.saudemin.hds.exceptions.AttachingInquiriesToAdminException;
import pt.saudemin.hds.exceptions.ErraticAnswerInputException;
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
import java.util.HashSet;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnswerChoiceRepository answerChoiceRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private Realm realm;

    @Autowired
    private Subject subject;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final int ADMIN_USER_ID = 170100228;
    private static final int NON_ADMIN_USER_ID = 170100231;

    @Before
    public void init() {
        var user = userRepository.findByPersonalId(NON_ADMIN_USER_ID).get();

        ((SimpleAccountRealm) realm).addAccount(user.getPersonalId().toString(), user.getPassword());
        subject.login(new UsernamePasswordToken(user.getPersonalId().toString(), user.getPassword()));
    }

    @Test
    public void testGetUserById() {
        var personalId = NON_ADMIN_USER_ID;

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
        }}, NON_ADMIN_USER_ID);

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
        var id = ADMIN_USER_ID;

        Assert.assertTrue(userService.delete(id));
        Assert.assertNull(userService.getByPersonalId(id));
    }

    @Test
    public void testLoginUser() {
        var loginDTO = new LoginDTO(ADMIN_USER_ID, "123456");
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
        Assert.assertTrue(userService.isIdDuplicate(ADMIN_USER_ID));
    }

    @Test
    public void testChangePassword() {
        var changePasswordDTO = new ChangePasswordDTO(ADMIN_USER_ID, "123456", "abcdef");
        Assert.assertTrue(userService.changeUserPassword(changePasswordDTO));

        var user = userRepository.findByPersonalId(changePasswordDTO.getPersonalId());

        Assert.assertTrue(user.isPresent());
        Assert.assertTrue(bCryptPasswordEncoder.matches(changePasswordDTO.getNewPassword(), user.get().getPassword()));
    }

    @Test
    public void testSetAnswers() throws PossibleAnswersExceededException, NotAssociatedToInquiryException,
            ErraticAnswerInputException {
        var userEntity = userRepository.findByPersonalId(NON_ADMIN_USER_ID).get();
        userEntity.setInquiries(new HashSet<Inquiry>() {{
            add(new Inquiry(1L, null, null, null));
            add(new Inquiry(2L, null, null,null));
        }});

        userRepository.save(userEntity);

        var genericQuestions = questionService.getAllGenericQuestions();
        var choiceQuestions = questionService.getAllChoiceQuestions();

        var userDTO = userService.getByPersonalId(NON_ADMIN_USER_ID);
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

        Assert.assertTrue(firstAnswer.get() instanceof FreeAnswer);
        Assert.assertTrue(secondAnswer.get() instanceof FreeAnswer);
        Assert.assertTrue(thirdAnswer.get() instanceof ChoiceAnswer);
    }

    @Test(expected = ErraticAnswerInputException.class)
    public void testSetAnswerCrashesWhenNonExistentId() throws PossibleAnswersExceededException, NotAssociatedToInquiryException,
            ErraticAnswerInputException {
        var userDTO = userService.getByPersonalId(NON_ADMIN_USER_ID);
        var nonExistentQuestion = new QuestionDTO(7L, null, null);

        List<AnswerDTO> answers = new ArrayList<AnswerDTO>() {{
            add(new FreeAnswerDTO(new AnswerIdDTO(nonExistentQuestion, userDTO), null, "Atm XD"));
        }};

        userService.setUserAnswersToQuestions(answers);
    }

    @Test(expected = ErraticAnswerInputException.class)
    public void testSetAnswerCrashesWhenIllegalAnswerProvided() throws PossibleAnswersExceededException, NotAssociatedToInquiryException,
            ErraticAnswerInputException {
        var userDTO = userService.getByPersonalId(NON_ADMIN_USER_ID);
        var choiceQuestion = questionService.getById(3);

        List<AnswerDTO> answers = new ArrayList<AnswerDTO>() {{
            add(new FreeAnswerDTO(new AnswerIdDTO(choiceQuestion, userDTO), null, "Atm XD"));
        }};

        userService.setUserAnswersToQuestions(answers);
    }

    @Test(expected = NotAssociatedToInquiryException.class)
    public void testSetAnswerCrashesWhenUserNotAuthorized() throws PossibleAnswersExceededException, NotAssociatedToInquiryException,
            ErraticAnswerInputException {
        var userDTO = userService.getByPersonalId(NON_ADMIN_USER_ID);
        var choiceQuestion = questionService.getById(3);

        List<AnswerDTO> answers = new ArrayList<AnswerDTO>() {{
            add(new ChoiceAnswerDTO(new AnswerIdDTO(choiceQuestion, userDTO), "Atm XD", new ArrayList<AnswerChoiceDTO>(){{
                add(AnswerChoiceMapper.INSTANCE.answerChoiceToAnswerChoiceDTO(answerChoiceRepository.findById(1L).get()));
                add(AnswerChoiceMapper.INSTANCE.answerChoiceToAnswerChoiceDTO(answerChoiceRepository.findById(2L).get()));
            }}));
        }};

        userService.setUserAnswersToQuestions(answers);
    }

    @Test(expected = PossibleAnswersExceededException.class)
    public void testSetAnswerCrashesWhenPossibleAnswersExceeded() throws PossibleAnswersExceededException, NotAssociatedToInquiryException,
            ErraticAnswerInputException {
        var user = userRepository.findByPersonalId(NON_ADMIN_USER_ID).get();
        user.setInquiries(new HashSet<Inquiry>() {{
            add(new Inquiry(1L, null, null, null));
            add(new Inquiry(2L, null, null,null));
        }});

        userRepository.save(user);

        var userDTO = userService.getByPersonalId(NON_ADMIN_USER_ID);
        var choiceQuestion = questionService.getById(3);

        List<AnswerDTO> answers = new ArrayList<AnswerDTO>() {{
            add(new ChoiceAnswerDTO(new AnswerIdDTO(choiceQuestion, userDTO), "Atm XD", new ArrayList<AnswerChoiceDTO>(){{
                add(AnswerChoiceMapper.INSTANCE.answerChoiceToAnswerChoiceDTO(answerChoiceRepository.findById(1L).get()));
                add(AnswerChoiceMapper.INSTANCE.answerChoiceToAnswerChoiceDTO(answerChoiceRepository.findById(2L).get()));
                add(AnswerChoiceMapper.INSTANCE.answerChoiceToAnswerChoiceDTO(answerChoiceRepository.findById(3L).get()));
                add(AnswerChoiceMapper.INSTANCE.answerChoiceToAnswerChoiceDTO(answerChoiceRepository.findById(4L).get()));
            }}));
        }};

        userService.setUserAnswersToQuestions(answers);
    }
}
