package com.runtik.dbcoursework.controller;

import com.runtik.dbcoursework.dto.TaskDTO;
import com.runtik.dbcoursework.dto.TaskUpdateStatusDTO;
import com.runtik.dbcoursework.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.annotation.security.RolesAllowed;
import java.util.List;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping(path = "task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping
    @RolesAllowed({"ROLE_BOSS"})
    public ResponseEntity<String> create(@RequestBody TaskDTO task){
        try {
            taskService.create(task);
            return new ResponseEntity<>("Task created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>("Failed to create task", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping(path = "updateStatus")
    @RolesAllowed({"ROLE_BOSS", "ROLE_ARENDATOR", "ROLE_HELPER", "ROLE_EMPLOYEE"})
    public void updateStatus(@RequestBody TaskUpdateStatusDTO taskUpdateStatusDTO){
        taskService.updateStatus(taskUpdateStatusDTO.getId(), taskUpdateStatusDTO.getNewStatus());
    }
    @GetMapping(path = "get/{limit}/{offset}")
    @RolesAllowed({"ROLE_BOSS"})
    public ResponseEntity<List<TaskDTO>> get(@RequestParam int limit, @RequestParam int offset){
        try {
            return new ResponseEntity<>(taskService.getTasks(limit, offset), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("getMy/{id}/{limit}/{offset}")
    @RolesAllowed({"ROLE_BOSS", "ROLE_ARENDATOR", "ROLE_HELPER", "ROLE_EMPLOYEE"})
    public ResponseEntity<List<TaskDTO>> get(@RequestParam Integer id, @RequestParam int limit, @RequestParam int offset){
        try {
            return new ResponseEntity<>(taskService.getMy(id, limit, offset), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
