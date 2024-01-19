package com.runtik.dbcoursework.controller;

import com.runtik.dbcoursework.dto.TestResultDTO;
import com.runtik.dbcoursework.dto.TestResultSelectDTO;
import com.runtik.dbcoursework.service.TestResultService;
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

@RestController
@RequestMapping(path = "testResult")
@SecurityRequirement(name = "javainuseapi")
@Log4j2
public class TestResultController {

    @Autowired
    private TestResultService testResultService;

    @GetMapping()
    @RolesAllowed({"ROLE_BOSS", "ROLE_EMPLOYEE"})
    public ResponseEntity<List<TestResultSelectDTO>> get(@PageableDefault Pageable pageable,
                                                         @RequestParam(required = false)
                                                         String[] filter) {
        try {
            return new ResponseEntity<>(testResultService.getTestResult(pageable, filter),
                                        HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping()
    @RolesAllowed({"ROLE_BOSS", "ROLE_EMPLOYEE"})
    public ResponseEntity<String> create(@RequestBody TestResultDTO testResult) {
        try {
            testResultService.create(testResult);
            return new ResponseEntity<>("Result added successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>("Failed to add result", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
