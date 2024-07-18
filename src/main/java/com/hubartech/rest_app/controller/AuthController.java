package com.hubartech.rest_app.controller;

import com.hubartech.rest_app.dto.UserDto;
import com.hubartech.rest_app.enums.UserRole;
import com.hubartech.rest_app.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> userRegister(@RequestBody UserDto userDto) {
        return authenticationService.register(userDto, UserRole.USER);
    }

    @PostMapping("/admin/register")
    public ResponseEntity<?> adminRegister(@RequestBody UserDto userDto) {
        return authenticationService.register(userDto, UserRole.ADMIN);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> getAuthenticate(@RequestBody UserDto userDto) {
        return authenticationService.authenticateUser(userDto);
    }
}