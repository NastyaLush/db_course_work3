package com.runtik.dbcoursework.controller;

import com.runtik.dbcoursework.service.NotificationsService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("notifications")
@SecurityRequirement(name = "javainuseapi")
@Log4j2
public class NotificationsController {

    private final NotificationsService service;

    public NotificationsController(NotificationsService service) {
        this.service = service;
    }


    @GetMapping()
    public ResponseEntity<?> getNewNotifications(@RequestParam("userId") int userId) {
        var res = this.service.getNewNotifications(userId);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
