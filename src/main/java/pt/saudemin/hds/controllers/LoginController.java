package pt.saudemin.hds.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import pt.saudemin.hds.dtos.LoginDTO;
import pt.saudemin.hds.dtos.LoginInfoDTO;
import pt.saudemin.hds.services.UserService;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "login")
    public ResponseEntity<LoginInfoDTO> doLogin(@RequestBody @Valid LoginDTO loginDTO) {
        return Optional
                .ofNullable(userService.authenticateUser(loginDTO))
                .map(ResponseEntity.ok()::body)
                .orElseGet(() -> ResponseEntity.status(401).build());
    }
}
