package pt.saudemin.hds.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pt.saudemin.hds.controllers.admin.base.BaseAdminController;
import pt.saudemin.hds.dtos.UserDTO;

import pt.saudemin.hds.services.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class AdminUserController extends BaseAdminController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "users")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(value = "user/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") int id) {
        return Optional
                .ofNullable(userService.getByPersonalId(id))
                .map(ResponseEntity.ok()::body)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(value = "user")
    public ResponseEntity<Object> createUser(@RequestBody @Valid UserDTO userDTO) {
        return Optional
                .ofNullable((Object) userService.create(userDTO))
                .map(ResponseEntity.ok()::body)
                .orElseGet(() -> ResponseEntity.badRequest().body("Administrators can't have attached inquiries!"));
    }
}
