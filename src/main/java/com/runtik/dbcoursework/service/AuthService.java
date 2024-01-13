package com.runtik.dbcoursework.service;

import com.runtik.dbcoursework.dto.AuthenticationResponse;
import com.runtik.dbcoursework.dto.LoginRequest;
import com.runtik.dbcoursework.dto.SignUpRequest;
import com.runtik.dbcoursework.enums.Role;
import com.runtik.dbcoursework.repository.PersonRepository;
import com.runtik.dbcoursework.security.CustomUserDetails;
import com.runtik.dbcoursework.security.JwtService;
import com.runtik.dbcoursework.tables.pojos.Person;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse authenticate(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getName(),
                        loginRequest.getPassword()
                )
        );
        var person = personRepository.getPersonByName(loginRequest.getName()).orElseThrow();
        var jwtToken = jwtService.generateToken(new CustomUserDetails(person));
        return AuthenticationResponse.builder()
                                     .token(jwtToken)
                                     .build();
    }

    public AuthenticationResponse register(SignUpRequest signUpRequest) {
        Person person = new Person();
        person.setPersonFractionId(signUpRequest.getFractionId());
        person.setPersonName(signUpRequest.getName());
        person.setPersonPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        person.setPersonRole(Role.user);

        personRepository.createPerson(person);
        var jwtToken = jwtService.generateToken(new CustomUserDetails(person));
        return AuthenticationResponse.builder()
                                     .token(jwtToken)
                                     .build();
    }
}
