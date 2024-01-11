package com.runtik.dbcoursework.controller;

import com.runtik.dbcoursework.dto.ReportDTO;
import com.runtik.dbcoursework.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.annotation.security.RolesAllowed;
import java.util.List;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping(path = "report")
public class ReportController {
    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping(path = "create")
    @RolesAllowed({"ROLE_BOSS", "ROLE_ARENDATOR", "ROLE_HELPER", "ROLE_EMPLOYEE"})
    public ResponseEntity<String> create(@RequestBody ReportDTO report){
        try {
            reportService.createReport(report);
            return new ResponseEntity<>("Report created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>("Failed to create report", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(path = "get/{limit}/{offset}")
    @RolesAllowed({"ROLE_BOSS", "ROLE_ARENDATOR", "ROLE_HELPER", "ROLE_EMPLOYEE"})
    public ResponseEntity<List<ReportDTO>> get(@RequestParam int limit, @RequestParam int offset){
        try {
            return new ResponseEntity<>(reportService.getReport(limit, offset), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
