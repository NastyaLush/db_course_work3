package com.runtik.dbcoursework.controller;

import com.runtik.dbcoursework.dto.AuthResponse;
import com.runtik.dbcoursework.dto.LoginRequest;
import com.runtik.dbcoursework.dto.SignUpRequest;
import com.runtik.dbcoursework.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.authenticate(loginRequest));
    }


    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> register(@RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok(authService.register(signUpRequest));
    }
}
