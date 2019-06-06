package pt.saudemin.hds.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pt.saudemin.hds.dtos.UserDTO;

import pt.saudemin.hds.services.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "user/{id}", method = RequestMethod.GET)
    public UserDTO getUserById(@PathVariable("id") int id) {
        return userService.getById(id);
    }
}
