package com.chat.ai.controller;

import com.chat.ai.models.api.request.CreateUserRequest;
import com.chat.ai.models.api.response.CreateUserResponse;
import com.chat.ai.service.UsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UsersController {
    private final UsersService usersService;
    private final PasswordEncoder passwordEncoder;

    public UsersController(UsersService usersService, PasswordEncoder passwordEncoder) {
        this.usersService = usersService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/create")
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody CreateUserRequest request) {
        String encryptedPass = passwordEncoder.encode(request.getPassword());
        request.setPassword(encryptedPass);
        CreateUserResponse response = usersService.createUser(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
