package pt.saudemin.hds.services;

import lombok.var;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import pt.saudemin.hds.dtos.ChangePasswordDTO;
import pt.saudemin.hds.dtos.entities.InquiryDTO;
import pt.saudemin.hds.dtos.UpdateUserDTO;
import pt.saudemin.hds.dtos.entities.UserDTO;
import pt.saudemin.hds.dtos.login.LoginDTO;
import pt.saudemin.hds.repositories.UserRepository;

import javax.transaction.Transactional;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

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
    public void testCreateUser() {
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
    public void testUpdateUser() {
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

        var isUserDeleted = userService.delete(id);
        var user = userService.getByPersonalId(id);

        Assert.assertTrue(isUserDeleted);
        Assert.assertNull(user);
    }

    @Test
    public void testLoginUser() {
        var loginDTO = new LoginDTO(170100228, "123456");
        var loginInfoDTO = userService.authenticateUser(loginDTO);

        Assert.assertNotNull(loginInfoDTO);
        Assert.assertNull(loginInfoDTO.getToken());
        Assert.assertEquals(loginInfoDTO.getPersonalId(), loginDTO.getPersonalId());
        Assert.assertEquals(loginInfoDTO.getIsAdmin(), true);
    }

    @Test
    public void testCheckDuplicateIds() {
        var id = 2L;
        var isDuplicate = userService.isIdDuplicate(id);

        Assert.assertTrue(isDuplicate);
    }

    @Test
    public void testChangePassword() {
        var changePasswordDTO = new ChangePasswordDTO(170100228, "123456", "abcdef");

        var changedPassword = userService.setUserPassword(changePasswordDTO);
        Assert.assertTrue(changedPassword);

        var user = userRepository.findByPersonalId(changePasswordDTO.getPersonalId());
        Assert.assertTrue(user.isPresent());

        var userWithChangedPassword = user.get().getPassword();
        Assert.assertTrue(bCryptPasswordEncoder.matches(changePasswordDTO.getNewPassword(), userWithChangedPassword));
    }
}
