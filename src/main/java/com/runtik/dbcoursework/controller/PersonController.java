package com.runtik.dbcoursework.controller;

import com.runtik.dbcoursework.Page;
import com.runtik.dbcoursework.dto.PersonCreateDTO;
import com.runtik.dbcoursework.dto.PersonRoleUpdateDTO;
import com.runtik.dbcoursework.dto.PersonSelectDTO;
import com.runtik.dbcoursework.service.PersonService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

import lombok.extern.log4j.Log4j2;


@CrossOrigin(origins = "*", maxAge = 3600)
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

    @GetMapping()
//    @RolesAllowed("ROLE_BOSS")
    public ResponseEntity<Page<List<PersonSelectDTO>>> get(
            @PageableDefault Pageable pageable,
            @RequestParam(required = false)
            String[] filter) {
        try {
            return new ResponseEntity<>(restService.getPersons(pageable, filter), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping()
//    @RolesAllowed({"ROLE_BOSS"})
    public void updateRole(@RequestBody PersonRoleUpdateDTO personRoleUpdateDTO) {
        restService.changePersonRole(personRoleUpdateDTO.getPersonID(), personRoleUpdateDTO.getRole());
    }

}
