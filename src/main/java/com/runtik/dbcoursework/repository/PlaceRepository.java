package com.runtik.dbcoursework.repository;

import com.runtik.dbcoursework.Page;
import com.runtik.dbcoursework.Routines;
import com.runtik.dbcoursework.Tables;
import com.runtik.dbcoursework.dto.CharacteristicDTO;
import com.runtik.dbcoursework.dto.EventPlaceDTO;
import com.runtik.dbcoursework.dto.PlaceDTO;
import com.runtik.dbcoursework.tables.pojos.GetPlaceCharacteristics;
import com.runtik.dbcoursework.tables.records.GetPlaceCharacteristicsRecord;
import com.runtik.dbcoursework.tables.records.PlaceRecord;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.SelectConditionStep;
import org.jooq.SortField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class PlaceRepository {

    @Autowired
    private DSLContext dslContext;

    public Page<List<PlaceDTO>> getPlaces(Pageable pageable, List<SortField<?>> sortFields, Condition condition) {
        try (var table = dslContext.selectFrom(Tables.PLACE)) {
            SelectConditionStep<PlaceRecord> placeRecordSelectConditionStep = table.where(condition);
            return new Page<>(placeRecordSelectConditionStep
                                   .orderBy(sortFields)
                                   .limit(pageable.getPageSize())
                                   .offset(
                                           pageable.getPageNumber() * pageable.getPageSize())
                                   .fetchInto(PlaceDTO.class),
                              dslContext.fetchCount(placeRecordSelectConditionStep
                              ));
        }
    }

    public Page<List<PlaceDTO>> getFree(LocalDateTime from, LocalDateTime to, Pageable pageable) {
        try (var table = dslContext.selectFrom(Routines.getFreePlaces(from, to))) {
            return new Page<>(table.limit(pageable.getPageSize())
                                   .offset(pageable.getPageSize() * pageable.getPageNumber())
                                   .fetchInto(PlaceDTO.class),
                              dslContext.fetchCount(table));
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

    public Page<List<GetPlaceCharacteristics>> getPlaceCharestiristic(Integer placeId, Pageable pageable,
                                                                      List<SortField<?>> sortFields,
                                                                      Condition condition) {
        try (var table = dslContext.selectFrom(Routines.getPlaceCharacteristics(placeId))) {
            SelectConditionStep<GetPlaceCharacteristicsRecord> getPlaceCharacteristicsRecords = table.where(condition);
            return new Page<>(getPlaceCharacteristicsRecords
                                   .orderBy(sortFields)
                                   .limit(pageable.getPageSize())
                                   .offset(pageable.getPageSize() * pageable.getPageNumber())
                                   .fetchInto(GetPlaceCharacteristics.class),
                              dslContext.fetchCount(getPlaceCharacteristicsRecords));
        }
    }
}
