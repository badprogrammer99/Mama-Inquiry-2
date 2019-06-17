package pt.saudemin.hds.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import pt.saudemin.hds.config.Constants;
import pt.saudemin.hds.dtos.ChangePasswordDTO;
import pt.saudemin.hds.dtos.login.LoginDTO;
import pt.saudemin.hds.services.UserService;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class CommonController {

    @Autowired
    private UserService userService;

    @PostMapping(value = Constants.LOGIN_PATH)
    public ResponseEntity<Object> doLogin(@RequestBody @Valid LoginDTO loginDTO) {
        return Optional
                .ofNullable(userService.authenticateUser(loginDTO))
                .map(loginInfoDTO -> new ResponseEntity<Object>(loginInfoDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>("Invalid credentials.", HttpStatus.FORBIDDEN));
    }

    @PostMapping(value = Constants.CHANGE_PASSWORD_PATH)
    public ResponseEntity<Object> changePassword(@RequestBody @Valid ChangePasswordDTO changePasswordDTO) {
        return Optional
                .ofNullable(userService.changeUserPassword(changePasswordDTO))
                .map(changedPassword -> new ResponseEntity<Object>(true, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(false, HttpStatus.FORBIDDEN));
    }
}
