package com.runtik.dbcoursework.controller;

import com.runtik.dbcoursework.dto.PersonCreateDTO;
import com.runtik.dbcoursework.dto.PersonRoleUpdateDTO;
import com.runtik.dbcoursework.dto.PersonSelectDTO;
import com.runtik.dbcoursework.service.PersonService;
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

    @GetMapping()
    @RolesAllowed("ROLE_BOSS")
    public ResponseEntity<List<PersonSelectDTO>> get(@RequestParam int limit, @RequestParam int offset,
                                                     @RequestParam(required = false)
                                                     String[] sort,
                                                     @RequestParam(required = false)
                                                     String[] filter) {
        try {
            return new ResponseEntity<>(restService.getPersons(limit, offset,sort,filter), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping()
    @RolesAllowed({"ROLE_BOSS"})
    public void updateRole(@RequestBody PersonRoleUpdateDTO personRoleUpdateDTO) {
        restService.changePersonRole(personRoleUpdateDTO.getPersonID(), personRoleUpdateDTO.getRole());
    }

}
