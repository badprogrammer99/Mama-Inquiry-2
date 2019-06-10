package pt.saudemin.hds.services.impl;

import lombok.extern.slf4j.Slf4j;
import lombok.var;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import pt.saudemin.hds.dtos.UpdateUserDTO;
import pt.saudemin.hds.dtos.login.LoginDTO;
import pt.saudemin.hds.dtos.login.LoginInfoDTO;
import pt.saudemin.hds.dtos.UserDTO;
import pt.saudemin.hds.entities.Inquiry;
import pt.saudemin.hds.entities.base.Answer;
import pt.saudemin.hds.exceptions.AttachingInquiriesToAdminException;
import pt.saudemin.hds.mappers.UserMapper;
import pt.saudemin.hds.repositories.UserRepository;
import pt.saudemin.hds.services.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

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
    public UserDTO create(UserDTO userDTO) {
        if (userDTO.getIsAdmin() && userDTO.getInquiries() != null) throw new AttachingInquiriesToAdminException();

        var user = UserMapper.INSTANCE.userDTOToUser(userDTO);
        user.setPassword(bCryptPasswordEncoder.encode(RandomStringUtils.random(10, true, true)));
        return UserMapper.INSTANCE.userToUserDTO(userRepository.save(user));
    }

    @Override
    public UserDTO update(UpdateUserDTO updateUserDTO) {
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
            return new LoginInfoDTO(null, user.get().getPersonalId(), user.get().getIsAdmin());
        }

        return null;
    }

    @Override
    public Boolean checkForDuplicateIds(long id) {
        return (!userRepository.findById(id).isPresent());
    }

    @Override
    public Boolean setUserPassword(int personalId, String oldPassword, String newPassword) {
        var user = userRepository.findByPersonalId(personalId);

        if (user.isPresent() && bCryptPasswordEncoder.matches(oldPassword, user.get().getPassword())) {
            user.get().setPassword(bCryptPasswordEncoder.encode(newPassword));
            userRepository.save(user.get());
            return true;
        }

        return false;
    }

    @Override
    public Boolean sendEmailWithUserDetails(long id) {
        throw new UnsupportedOperationException("Operation not supported yet.");
    }

    @Override
    public Boolean setUserAnswersToQuestionnaire(List<Answer> answers) {
        throw new UnsupportedOperationException("Operation not supported yet.");
    }
}
