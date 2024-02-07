package com.runtik.dbcoursework.controller;

import com.runtik.dbcoursework.Page;
import com.runtik.dbcoursework.dto.TaskDTO;
import com.runtik.dbcoursework.dto.TaskCreateDTO;
import com.runtik.dbcoursework.dto.TaskUpdateStatusDTO;
import com.runtik.dbcoursework.service.TaskService;
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
@RestController
@Log4j2
@SecurityRequirement(name = "javainuseapi")
@RequestMapping(path = "task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    @RolesAllowed({"ROLE_BOSS"})
    public ResponseEntity<String> create(@RequestBody TaskCreateDTO task) {
        try {
            taskService.create(task);
            return new ResponseEntity<>("Task created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>("Failed to create task", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping()
    @RolesAllowed({"ROLE_BOSS", "ROLE_ARENDATOR", "ROLE_HELPER", "ROLE_EMPLOYEE"})
    public void updateStatus(@RequestBody TaskUpdateStatusDTO taskUpdateStatusDTO) {
        taskService.updateStatus(taskUpdateStatusDTO.getId(), taskUpdateStatusDTO.getNewStatus());
    }

    @GetMapping()
    @RolesAllowed({"ROLE_BOSS"})

    public ResponseEntity<Page<List<TaskDTO>>> get(@PageableDefault Pageable pageable,
                                                   @RequestParam(required = false)
                                             String[] filter) {
        try {
            System.out.println(taskService.getTasks(pageable, filter));
            return new ResponseEntity<>(taskService.getTasks(pageable, filter), HttpStatus.OK);

        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{id}")
    @RolesAllowed({"ROLE_BOSS"})
    public ResponseEntity<TaskDTO> getById(@PathVariable int id) {
        try {
            return new ResponseEntity<>(taskService.getTaskById(id), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
