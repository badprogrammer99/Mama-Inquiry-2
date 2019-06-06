package pt.saudemin.hds.services.impl;

import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import pt.saudemin.hds.dtos.UserDTO;
import pt.saudemin.hds.entities.base.Answer;
import pt.saudemin.hds.mappers.UserMapper;
import pt.saudemin.hds.repositories.UserRepository;
import pt.saudemin.hds.services.UserService;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO getById(long id) {
        var user = userRepository.findById(id).orElse(null);

        return userMapper.userToUserDTO(user);
    }

    @Override
    public UserDTO createNew(UserDTO userDTO) {
        var user = userMapper.userDTOToUser(userDTO);

        return userMapper.userToUserDTO(userRepository.save(user));
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
            log.error("Error changing password of a User. Exception details: " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public Boolean sendEmailWithUserDetails(long id) {
        return null;
    }

    @Override
    public Boolean setUserAnswersToQuestionnaire(List<Answer> answers) {
        return null;
    }
}
