package com.hubartech.rest_app.service;

import com.hubartech.rest_app.dto.UserDto;
import com.hubartech.rest_app.enums.UserRole;
import com.hubartech.rest_app.model.User;
import com.hubartech.rest_app.model.UserInfo;
import com.hubartech.rest_app.repository.UserInfoRepository;
import com.hubartech.rest_app.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserInfoRepository userInfoRepository;

    public AuthenticationService(UserRepository userRepository,
                                 PasswordEncoder passwordEncoder, JwtService jwtService,
                                 AuthenticationManager authenticationManager, UserInfoRepository userInfoRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userInfoRepository = userInfoRepository;
    }

    public boolean isValidReg(UserDto userDto) {
        return userDto.getEmail() != null && userDto.getPassword() != null;
    }

    public boolean isValidAuth(UserDto userDto) {
        return (userDto.getEmail() != null || userDto.getPhoneNumber() != null) && userDto.getPassword() != null;
    }

    public ResponseEntity<?> register(UserDto userDto, UserRole userRole) {
        HashMap<String, String> response = new HashMap<>();
        if (isValidReg(userDto)) {
            try {
                User user = new User();
                user.setEmail(userDto.getEmail());
                user.setPhoneNumber(userDto.getPhoneNumber());
                user.setPassword(passwordEncoder.encode(userDto.getPassword()));
                user.setRole(userRole);
                userRepository.create(user);

                UserInfo userInfo = new UserInfo();
                userInfo.setUser(userRepository.findByEmail(userDto.getEmail()));
                userInfoRepository.create(userInfo);

                response.put("message", "Registration successful.");
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                response.put("message", "email or phoneNumber already exists");
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }
        } else {
            response.put("message", "Please provide both email and password, phoneNumber optional.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> authenticateUser(UserDto userDto) {
        HashMap<String, String> response = new HashMap<>();
        if (isValidAuth(userDto)) {
            User user;
            if (userDto.getEmail() == null) {
                try{
                    user = userRepository.findByPhoneNumber(userDto.getPhoneNumber());
                    userDto.setEmail(user.getEmail());
                }
                catch(Exception e){
                    System.out.println(e.getMessage());
                    response.put("message", "Invalid email or password");
                    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                }
            } else{
                try {
                    user = userRepository.findByEmail(userDto.getEmail());
                }
                catch(Exception e){
                    System.out.println(e.getMessage());
                    response.put("message", "Invalid email or password");
                    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                }
            }
            try {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword());
                authenticationManager.authenticate(authToken);
                String token = jwtService.generateToken(user);
                response.put("token", token);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                response.put("message", "Invalid password.");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
        } else {
            response.put("message", "Please provide email or phoneNumber and password.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}