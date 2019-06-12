package pt.saudemin.hds.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping(value = "teste")
    public ResponseEntity<Object> testController() {
        return new ResponseEntity<>("Teste!", HttpStatus.OK);
    }
}
