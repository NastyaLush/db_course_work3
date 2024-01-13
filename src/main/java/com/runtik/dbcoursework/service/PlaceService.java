package com.runtik.dbcoursework.service;

import com.runtik.dbcoursework.Tables;
import com.runtik.dbcoursework.Util;
import com.runtik.dbcoursework.dto.CharacteristicDTO;
import com.runtik.dbcoursework.dto.EventPlaceDTO;
import com.runtik.dbcoursework.dto.PlaceDTO;
import com.runtik.dbcoursework.repository.PlaceRepository;
import com.runtik.dbcoursework.tables.pojos.GetPlaceCharacteristics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PlaceService {
    @Autowired
    private PlaceRepository placeRepository;
    public List<PlaceDTO> getPlaces(int limit, int offset, String[] sortFields,String[]filter){
        return placeRepository.getPlaces(limit, offset, Util.getSortedFields(sortFields, Tables.PLACE), Util.getFilterFields(filter, Tables.PLACE));
    }
    public List<PlaceDTO> getFree(LocalDateTime from, LocalDateTime to, int limit, int offset){
        return placeRepository.getFree(from,to, limit, offset);
    }
    public void create(PlaceDTO place){
        placeRepository.create(place);
   }
    public void createCharectiristic(CharacteristicDTO characteristic){
        placeRepository.createCharectiristic(characteristic);
    }
    public void addChar(Integer placeId, String characteristic){
        placeRepository.addChar(placeId,characteristic);
    }
    public void arendate(Integer placeId,
                         Integer arendatorId,
                         LocalDateTime fromTime,
                         LocalDateTime toTime){
        placeRepository.arendate(placeId,arendatorId,fromTime,toTime);
   }
    public void addPlaceToEvent(EventPlaceDTO eventPlace){
        placeRepository.addPlaceToEvent(eventPlace);
    }
    public List<GetPlaceCharacteristics> getPlaceCharestiristic(Integer placeId, int limit, int offset, String[] sortFields,String[]filter){
        return placeRepository.getPlaceCharestiristic(placeId, limit, offset,  Util.getSortedFields(sortFields, Tables.GET_PLACE_CHARACTERISTICS), Util.getFilterFields(filter, Tables.GET_PLACE_CHARACTERISTICS));
    }
}
