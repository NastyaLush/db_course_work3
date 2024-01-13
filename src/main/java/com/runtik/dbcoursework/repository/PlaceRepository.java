package com.runtik.dbcoursework.repository;

import com.runtik.dbcoursework.Routines;
import com.runtik.dbcoursework.Tables;
import com.runtik.dbcoursework.dto.CharacteristicDTO;
import com.runtik.dbcoursework.dto.EventPlaceDTO;
import com.runtik.dbcoursework.dto.PlaceDTO;
import com.runtik.dbcoursework.tables.pojos.GetPlaceCharacteristics;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.SortField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class PlaceRepository {

    @Autowired
    private DSLContext dslContext;

    public List<PlaceDTO> getPlaces(int limit, int offset, List<SortField<?>> sortFields, Condition condition) {
        try (var table = dslContext.selectFrom(Tables.PLACE)) {
            return table.where(condition).orderBy(sortFields).limit(limit).offset(offset)
                    .fetchInto(PlaceDTO.class);
        }
    }

    public List<PlaceDTO> getFree(LocalDateTime from, LocalDateTime to, int limit, int offset) {
        try (var table = dslContext.selectFrom(Routines.getFreePlaces(from, to))) {
            return table.limit(limit).offset(offset).fetchInto(PlaceDTO.class);
        }
    }

    public void create(PlaceDTO place) {
        dslContext.insertInto(Tables.PLACE, Tables.PLACE.FRACTION_ID, Tables.PLACE.PLACE_NAME)
                  .values(place.getFractionId(), place.getPlaceName())
                  .execute();

    }

    public void createCharectiristic(CharacteristicDTO characteristic) {
        dslContext.insertInto(Tables.CHARACTERISTIC, Tables.CHARACTERISTIC.CHARECTIRISTIC_NAME)
                  .values(characteristic.getCharectiristicName())
                  .execute();

    }

    public void addChar(Integer placeId, String characteristic) {
        Routines.addCharToPlace(dslContext.configuration(), placeId, characteristic);
    }

    public void arendate(Integer placeId,
                         Integer arendatorId,
                         LocalDateTime fromTime,
                         LocalDateTime toTime) {
        Routines.arendatePlace(dslContext.configuration(), placeId, arendatorId, fromTime, toTime);
    }

    public void addPlaceToEvent(EventPlaceDTO eventPlace) {
        dslContext.insertInto(Tables.EVENT_PLACE, Tables.EVENT_PLACE.EVENT_ID,
                              Tables.EVENT_PLACE.PLACE_ARENDATOR_ID)
                  .values(eventPlace.getEventId(), eventPlace.getPlaceArendatorId())
                  .execute();

    }

    public List<GetPlaceCharacteristics> getPlaceCharestiristic(Integer placeId, int limit, int offset, List<SortField<?>> sortFields,Condition condition) {
        try (var table = dslContext.selectFrom(Routines.getPlaceCharacteristics(placeId))) {
            return table.where(condition).orderBy(sortFields).limit(limit).offset(offset).fetchInto(GetPlaceCharacteristics.class);
        }
    }
}
