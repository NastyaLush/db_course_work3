package com.runtik.dbcoursework.controller;

import com.runtik.dbcoursework.dto.EventDTO;
import com.runtik.dbcoursework.dto.ParticipantDTO;
import com.runtik.dbcoursework.service.EventService;
import com.runtik.dbcoursework.tables.pojos.GetEventParticipants;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import javax.annotation.security.RolesAllowed;
import java.util.List;
import lombok.extern.log4j.Log4j2;

@RestController()
@RequestMapping("event")
@SecurityRequirement(name = "javainuseapi")
@Log4j2
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping()
    @Secured("ROLE_BOSS")
    public ResponseEntity<String> create(@RequestBody EventDTO event) {
        try {
            eventService.create(event);
            return new ResponseEntity<>("event successfully created", HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>("failed to create event", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    @Secured("ROLE_BOSS")
    public ResponseEntity<List<EventDTO>> get(
            @PageableDefault Pageable pageable,
            @RequestParam(required = false) String[] filter
    ) {
        try {
            return new ResponseEntity<>(eventService.getEvents(pageable, filter), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "add_participant")
    @RolesAllowed({"ROLE_BOSS", "ROLE_EMPLOYEE"})
    public ResponseEntity<String> addParticipant(@RequestBody ParticipantDTO participantDTO) {
        try {
            eventService.addParticipant(participantDTO.getPersonId(), participantDTO.getEventId());
            return new ResponseEntity<>("participant successfully added", HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>("failed to add participant", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "get_participants")
    @RolesAllowed({"ROLE_BOSS", "ROLE_EMPLOYEE"})
    public ResponseEntity<List<GetEventParticipants>> getEventParticipants(@RequestParam Integer eventId,
                                                                           @PageableDefault Pageable pageable,
                                                                           @RequestParam(required = false)
                                                                           String[] filter) {
        try {
            return new ResponseEntity<>(eventService.getEventParticipants(eventId, pageable, filter),
                                        HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
