package pt.saudemin.hds.services.impl;

import lombok.extern.slf4j.Slf4j;
import lombok.var;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pt.saudemin.hds.dtos.LoginDTO;
import pt.saudemin.hds.dtos.LoginInfoDTO;
import pt.saudemin.hds.dtos.UserDTO;
import pt.saudemin.hds.entities.User;
import pt.saudemin.hds.entities.base.Answer;
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

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper.INSTANCE::userToUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getById(long id) {
        var user = userRepository.findById(id);

        return user.map(UserMapper.INSTANCE::userToUserDTO).orElse(null);
    }

    @Override
    public UserDTO getByPersonalId(int id) {
        var user = userRepository.findByPersonalId(id);

        return user.map(UserMapper.INSTANCE::userToUserDTO).orElse(null);
    }

    @Override
    public LoginInfoDTO authenticateUser(LoginDTO loginDTO) {
        var user = userRepository.findByPersonalId(loginDTO.getPersonalId());

        if (user.isPresent() && user.get().getPassword().equals(loginDTO.getPassword())) {
            return new LoginInfoDTO(null, user.get().getPersonalId(), user.get().getIsAdmin());
        }

        return null;
    }

    @Override
    public UserDTO create(UserDTO userDTO) {
        if (userDTO.getIsAdmin() && userDTO.getInquiries() != null) return null;

        var user = UserMapper.INSTANCE.userDTOToUser(userDTO);
        return UserMapper.INSTANCE.userToUserDTO(userRepository.save(user));
    }

    @Override
    public Boolean checkForDuplicateIds(long id) {
        return (!userRepository.findById(id).isPresent());
    }

    @Override
    public Boolean setUserPassword(String oldPassword, String newPassword) {
        var userByPass = userRepository.findByPassword(oldPassword);

        try {
            userByPass.ifPresent(user -> {
                userByPass.get().setPassword(newPassword);
                userRepository.save(userByPass.get());
            });
        } catch (Exception e) {
            log.error("Error changing password of an User. Exception details: " + e.getMessage());
            return false;
        }

        return true;
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
