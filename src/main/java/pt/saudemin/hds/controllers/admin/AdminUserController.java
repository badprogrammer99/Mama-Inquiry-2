package pt.saudemin.hds.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pt.saudemin.hds.controllers.admin.base.BaseAdminController;
import pt.saudemin.hds.dtos.UpdateUserDTO;
import pt.saudemin.hds.dtos.entities.UserDTO;

import pt.saudemin.hds.exceptions.AttachingInquiriesToAdminException;
import pt.saudemin.hds.services.UserService;

import javax.validation.Valid;

import java.util.Optional;

@RestController
public class AdminUserController extends BaseAdminController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "users")
    public ResponseEntity<Object> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping(value = "user/{id}")
    public ResponseEntity<Object> getUserByPersonalId(@PathVariable("id") int id) {
        return Optional
                .ofNullable(userService.getByPersonalId(id))
                .map(user -> new ResponseEntity<Object>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>("No records have been found.", HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "user")
    public ResponseEntity<Object> createUser(@RequestBody @Valid UserDTO userDTO) {
        ResponseEntity<Object> response;

        try {
            response = Optional
                        .ofNullable(userService.create(userDTO))
                        .map(user -> new ResponseEntity<Object>(user, HttpStatus.OK))
                        .get();
        } catch (AttachingInquiriesToAdminException e) {
            return new ResponseEntity<>("Admins can't have attached inquiries!", HttpStatus.BAD_REQUEST);
        }

        return response;
    }

    @PutMapping(value = "user")
    public ResponseEntity<Object> updateUser(@RequestBody @Valid UpdateUserDTO updateUserDTO) {
        return Optional
                .ofNullable(userService.update(updateUserDTO))
                .map(user -> new ResponseEntity<Object>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>("Couldn't update the user, " +
                        "either you didn't supply an ID or the record associated to it doesn't exist. ", HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping(value = "user")
    public ResponseEntity<Object> deleteUser(@RequestBody int personalId) {
        return userService.delete(personalId) ? new ResponseEntity<>(true, HttpStatus.OK) :
                new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
