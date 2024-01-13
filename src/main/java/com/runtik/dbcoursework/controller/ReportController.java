package com.runtik.dbcoursework.controller;

import com.runtik.dbcoursework.dto.ReportDTO;
import com.runtik.dbcoursework.service.ReportService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name = "javainuseapi")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping()
    @RolesAllowed({"ROLE_BOSS", "ROLE_ARENDATOR", "ROLE_HELPER", "ROLE_EMPLOYEE"})
    public ResponseEntity<String> create(@RequestBody ReportDTO report) {
        try {
            reportService.createReport(report);
            return new ResponseEntity<>("Report created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>("Failed to create report", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping()
    @RolesAllowed({"ROLE_BOSS", "ROLE_ARENDATOR", "ROLE_HELPER", "ROLE_EMPLOYEE"})
    public ResponseEntity<List<ReportDTO>> get(@RequestParam int limit, @RequestParam int offset,
                                               @RequestParam(required = false)
                                               String[] sort,
                                               @RequestParam(required = false)
                                               String[] filter) {
        try {
            return new ResponseEntity<>(reportService.getReport(limit, offset, sort, filter), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
