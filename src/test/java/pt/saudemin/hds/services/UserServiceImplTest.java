package pt.saudemin.hds.services;

import lombok.var;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import pt.saudemin.hds.dtos.InquiryDTO;
import pt.saudemin.hds.dtos.UpdateUserDTO;
import pt.saudemin.hds.dtos.UserDTO;
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

        Assert.assertEquals(userDTO.getPersonalId(), createdUser.getPersonalId());
        Assert.assertEquals(userDTO.getName(), createdUser.getName());
        Assert.assertEquals(userDTO.getIsAdmin(), createdUser.getIsAdmin());
        Assert.assertEquals(userDTO.getInquiries(), createdUser.getInquiries());
    }

    @Test
    public void testUpdateUser() {
        var userDTO = new UpdateUserDTO(null,  170100481, "Jajaers xd", false, new ArrayList<InquiryDTO>() {{
            new InquiryDTO(2L, null, null);
        }}, 170100231);
        var updateUser = userService.update(userDTO);
        var updatedUser = userService.getByPersonalId(170100481);

        Assert.assertNotNull(updateUser);
        Assert.assertEquals(updatedUser.getPersonalId(), userDTO.getPersonalId());
        Assert.assertEquals(updatedUser.getName(), userDTO.getName());
        Assert.assertEquals(updatedUser.getIsAdmin(), userDTO.getIsAdmin());
        Assert.assertEquals(updatedUser.getInquiries(), userDTO.getInquiries());
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
    public void testChangePassword() {
        var personalId = 170100228;

        var oldPassword = "123456";
        var newPassword = "abcdef";

        var changedPassword = userService.setUserPassword(personalId, oldPassword, newPassword);
        Assert.assertTrue(changedPassword);

        var user = userRepository.findByPersonalId(personalId);
        Assert.assertTrue(user.isPresent());

        var userWithChangedPassword = user.get().getPassword();
        Assert.assertTrue(bCryptPasswordEncoder.matches(newPassword, userWithChangedPassword));
    }
}
