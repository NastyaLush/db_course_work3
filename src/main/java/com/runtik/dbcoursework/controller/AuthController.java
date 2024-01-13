package com.runtik.dbcoursework.controller;

import com.runtik.dbcoursework.dto.AuthenticationResponse;
import com.runtik.dbcoursework.dto.LoginRequest;
import com.runtik.dbcoursework.dto.SignUpRequest;
import com.runtik.dbcoursework.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.authenticate(loginRequest));
    }


    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok(authService.register(signUpRequest));
    }
}
