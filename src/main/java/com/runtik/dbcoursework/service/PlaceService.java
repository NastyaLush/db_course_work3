package com.runtik.dbcoursework.service;

import com.runtik.dbcoursework.Routines;
import com.runtik.dbcoursework.Tables;
import com.runtik.dbcoursework.dto.CharacteristicDTO;
import com.runtik.dbcoursework.dto.EventPlaceDTO;
import com.runtik.dbcoursework.dto.PlaceDTO;
import com.runtik.dbcoursework.repository.PlaceRepository;
import com.runtik.dbcoursework.tables.pojos.Place;
import com.runtik.dbcoursework.tables.pojos.Characteristic;
import com.runtik.dbcoursework.tables.pojos.EventPlace;
import com.runtik.dbcoursework.tables.pojos.GetPlaceCharacteristics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PlaceService {
    @Autowired
    private PlaceRepository placeRepository;
    public List<PlaceDTO> get(){
        return placeRepository.get();
    }
    public List<PlaceDTO> getFree(Integer fractionId, LocalDateTime from, LocalDateTime to){
        return placeRepository.getFree(fractionId,from,to);
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
    public List<GetPlaceCharacteristics> getPlaceCharestiristic(Integer placeId){
        return placeRepository.getPlaceCharestiristic(placeId);
    }
}
