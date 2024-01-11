package com.runtik.dbcoursework.controller;

import com.runtik.dbcoursework.dto.TestResultDTO;
import com.runtik.dbcoursework.service.TestResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.annotation.security.RolesAllowed;
import java.util.List;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping(path = "testResult")
@Log4j2
public class TestResultController {

    @Autowired
    private TestResultService testResultService;

    @GetMapping("get/{id}/{limit}/{offset}")
    @RolesAllowed({"ROLE_BOSS", "ROLE_EMPLOYEE"})
    public ResponseEntity<List<TestResultDTO>> get(@RequestParam Integer id, @RequestParam int limit, @RequestParam int offset) {
        try {
            return new ResponseEntity<>(testResultService.getTestResult(id, limit, offset), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "create")
    @RolesAllowed({"ROLE_BOSS", "ROLE_EMPLOYEE"})
    public ResponseEntity<String> create(@RequestBody TestResultDTO testResult) {
        try {
            testResultService.create(testResult);
            return new ResponseEntity<>("Result added successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>("Failed to add result", HttpStatus.INTERNAL_SERVER_ERROR);
        } }
}
