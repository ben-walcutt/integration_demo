package co.newlabs.controller;

import co.newlabs.dto.UserDTO;
import co.newlabs.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private UserService service;

    @PostMapping("/signup")
    public ResponseEntity createNewUser(@RequestBody UserDTO user) {
        service.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
