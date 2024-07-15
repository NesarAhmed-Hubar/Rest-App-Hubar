package com.hubartech.rest_app.controller;

import com.hubartech.rest_app.dto.UserDto;
import com.hubartech.rest_app.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> postRegister(@RequestBody UserDto userDto) {
        return authenticationService.registerUser(userDto);
    }

    @GetMapping("/authenticate")
    public ResponseEntity<?> getAuthenticate(@RequestBody UserDto userDto) {
        return authenticationService.authenticateUser(userDto);
    }
}