package com.runtik.dbcoursework.service;

import com.runtik.dbcoursework.dto.*;
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

    public AuthResponse authenticate(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getName(),
                        loginRequest.getPassword()
                )
        );
        var person = personRepository.getPersonByName(loginRequest.getName())
                                     .orElseThrow();
        var jwtToken = jwtService.generateToken(new CustomUserDetails(person));
        return AuthResponse.builder()
                           .token(jwtToken)
                           .personSelectDTO(PersonSelectDTO.builder()
                                                           .personId(person.getPersonId())
                                                           .personFractionId(person.getPersonFractionId())
                                                           .personName(person.getPersonName())
                                                           .personRole(person.getPersonRole().getLiteral())
                                                           .build()

                           ).build();
    }

    public AuthResponse register(SignUpRequest signUpRequest) {
        Person person = new Person();
        person.setPersonFractionId(signUpRequest.getFractionId());
        person.setPersonName(signUpRequest.getName());
        person.setPersonPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        person.setPersonRole(Role.user);

        PersonSelectDTO personSelectDTO = personRepository.createPerson(person);
        var jwtToken = jwtService.generateToken(new CustomUserDetails(person));
        return AuthResponse.builder()
                           .token(jwtToken)
                           .personSelectDTO(PersonSelectDTO.builder()
                                                           .personId(personSelectDTO.getPersonId())
                                                           .personFractionId(personSelectDTO.getPersonFractionId())
                                                           .personName(personSelectDTO.getPersonName())
                                                           .personRole(personSelectDTO.getPersonRole())
                                                           .build()

                           ).build();

    }
}
