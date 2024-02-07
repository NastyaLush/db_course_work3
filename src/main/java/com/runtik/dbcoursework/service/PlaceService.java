package com.runtik.dbcoursework.service;

import com.runtik.dbcoursework.Page;
import com.runtik.dbcoursework.Tables;
import com.runtik.dbcoursework.Util;
import com.runtik.dbcoursework.dto.CharacteristicDTO;
import com.runtik.dbcoursework.dto.EventPlaceDTO;
import com.runtik.dbcoursework.dto.PlaceDTO;
import com.runtik.dbcoursework.repository.PlaceRepository;
import com.runtik.dbcoursework.tables.pojos.GetPlaceCharacteristics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PlaceService {

    @Autowired
    private PlaceRepository placeRepository;

    public Page<List<PlaceDTO>> getPlaces(Pageable pageable, String[] filter) {
        return placeRepository.getPlaces(pageable, Util.getSortedFields(pageable.getSort(), Tables.PLACE),
                                         Util.getFilterFields(filter, Tables.PLACE));

    }

    public Page<List<PlaceDTO>> getFree(LocalDateTime from, LocalDateTime to, Pageable pageable) {
        return placeRepository.getFree(from, to, pageable);
    }

    public void create(PlaceDTO place) {
        placeRepository.create(place);
    }

    public void createCharectiristic(CharacteristicDTO characteristic) {
        placeRepository.createCharectiristic(characteristic);
    }

    public void addChar(Integer placeId, String characteristic) {
        placeRepository.addChar(placeId, characteristic);
    }

    public void arendate(Integer placeId,
                         Integer arendatorId,
                         LocalDateTime fromTime,
                         LocalDateTime toTime) {
        placeRepository.arendate(placeId, arendatorId, fromTime, toTime);
    }

    public void addPlaceToEvent(EventPlaceDTO eventPlace) {
        placeRepository.addPlaceToEvent(eventPlace);
    }

    public Page<List<GetPlaceCharacteristics>> getPlaceCharestiristic(Integer placeId, Pageable pageable, String[] filter) {
        return placeRepository.getPlaceCharestiristic(placeId, pageable, Util.getSortedFields(pageable.getSort(),
                                                                                              Tables.GET_PLACE_CHARACTERISTICS),
                                                      Util.getFilterFields(filter, Tables.GET_PLACE_CHARACTERISTICS));
    }
}
