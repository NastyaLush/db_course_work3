package com.runtik.dbcoursework.controller;

import com.runtik.dbcoursework.dto.PersonDTO;
import com.runtik.dbcoursework.dto.PersonRoleUpdateDTO;
import com.runtik.dbcoursework.dto.TaskUpdateStatusDTO;
import com.runtik.dbcoursework.service.PersonService;
import com.runtik.dbcoursework.tables.pojos.Person;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.annotation.security.RolesAllowed;
import java.util.List;
import lombok.extern.log4j.Log4j2;


@RestController()
@Log4j2
@RequestMapping(path = "person")
@SecurityRequirement(name = "javainuseapi")
public class PersonController {

    private final PersonService restService;

    @Autowired
    public PersonController(PersonService restService) {
        this.restService = restService;
    }

    @PostMapping(path = "create")
    @RolesAllowed({"ROLE_BOSS", "ROLE_EMPLOYEE"})
    public ResponseEntity<String> create(@RequestBody PersonDTO person) {
        try {
            restService.insertPerson(person);
            return new ResponseEntity<>("Report created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>("Failed to create report", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(path = "get/{limit}/{offset}")
    @RolesAllowed("ROLE_BOSS")
    public ResponseEntity<List<PersonDTO>> get(@RequestParam int limit, @RequestParam int offset) {
        try {
            return new ResponseEntity<>(restService.getPersons(limit, offset), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping(path = "updateRole")
    @RolesAllowed({"ROLE_BOSS"})
    public void updateRole(@RequestBody PersonRoleUpdateDTO personRoleUpdateDTO){
        restService.changePersonRole(personRoleUpdateDTO.getPersonID(), personRoleUpdateDTO.getRole());
    }

}
