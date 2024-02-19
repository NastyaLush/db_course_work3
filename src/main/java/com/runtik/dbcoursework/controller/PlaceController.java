package com.runtik.dbcoursework.controller;

import com.runtik.dbcoursework.Page;
import com.runtik.dbcoursework.dto.*;
import com.runtik.dbcoursework.service.PlaceService;
import com.runtik.dbcoursework.tables.pojos.GetPlaceCharacteristics;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import lombok.extern.log4j.Log4j2;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("place")
@SecurityRequirement(name = "javainuseapi")
@Log4j2
public class PlaceController {

    @Autowired
    private PlaceService placeService;

    @GetMapping()
    @RolesAllowed({"ROLE_BOSS", "ROLE_ARENDATOR", "ROLE_EMPLOYEE"})
    public ResponseEntity<Page<List<PlaceDTO>>> get(
            @PageableDefault Pageable pageable,
            @RequestParam(required = false)
            String[] filter
    ) {
        try {
            return new ResponseEntity<>(placeService.getPlaces(pageable, filter), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @GetMapping("/{id}")
    public ResponseEntity<PlaceDTO> getById(@PathVariable int id) {
        return new ResponseEntity<>(placeService.getById(id), HttpStatus.OK);
    }

    @GetMapping(path = "getFree")
    @RolesAllowed({"ROLE_BOSS", "ROLE_ARENDATOR", "ROLE_EMPLOYEE"})
    public ResponseEntity<Page<List<PlaceDTO>>> getFree(
            @RequestParam(value = "from", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
            LocalDateTime from,
            @RequestParam(value = "to", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
            LocalDateTime to,
            @PageableDefault Pageable pageable
    ) {
        try {
            var _from = from != null ? from : LocalDateTime.now().minusDays(7);
            var _to = to != null ? to : LocalDateTime.now();
            return new ResponseEntity<>(placeService.getFree(_from, _to, pageable), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping()
    @RolesAllowed({"ROLE_BOSS", "ROLE_ARENDATOR", "ROLE_EMPLOYEE"})
    public ResponseEntity<String> create(@RequestBody PlaceDTO place) {
        try {
            placeService.create(place);
            return new ResponseEntity<>("place created", HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>("failed to create repository", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping(path = "createCharacteristic")
    @RolesAllowed({"ROLE_BOSS", "ROLE_ARENDATOR", "ROLE_EMPLOYEE"})
    public ResponseEntity<String> createCharectiristic(@RequestBody CharacteristicDTO characteristic) {
        try {
            placeService.createCharectiristic(characteristic);
            return new ResponseEntity<>("characteristic created", HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>("failed to create characteristic", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "addCharacteristic")
    @RolesAllowed({"ROLE_BOSS", "ROLE_EMPLOYEE"})
    public ResponseEntity<String> addChar(@RequestBody CharacteristicAddDTO characteristicAddDTO) {
        try {
            placeService.addChar(characteristicAddDTO.getPlaceId(), characteristicAddDTO.getCharacteristic());
            return new ResponseEntity<>("characteristic added", HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>("failed to add characteristic", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "arendate")
    public ResponseEntity<String> arendate(@RequestBody ArendateDTO arendateDTO) {
        try {
            var from = LocalDateTime.now();
            var to = LocalDateTime.now().plusDays(20);
            placeService.arendate(arendateDTO.getPlaceId(), arendateDTO.getArendatorId(), from,
                    to);
            return new ResponseEntity<>("place arendated", HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>("failed to arendate place", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "add_place_to_event")
    @RolesAllowed({"ROLE_BOSS", "ROLE_ARENDATOR", "ROLE_EMPLOYEE"})
    public ResponseEntity<String> addPlaceToEvent(@RequestBody EventPlaceDTO eventPlace) {
        try {
            placeService.addPlaceToEvent(eventPlace);
            return new ResponseEntity<>("place added to event", HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>("failed to add place toevent", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "get_place_characteristics")
    @RolesAllowed({"ROLE_BOSS", "ROLE_ARENDATOR", "ROLE_EMPLOYEE"})
    public ResponseEntity<Page<List<GetPlaceCharacteristics>>> getPlaceCharestiristic(
            @RequestParam Integer placeId,
            @PageableDefault Pageable pageable,
            @RequestParam(required = false)
            String[] filter
    ) {
        try {
            return new ResponseEntity<>(
                    placeService.getPlaceCharestiristic(placeId, pageable, filter),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
