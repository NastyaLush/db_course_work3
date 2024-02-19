package com.runtik.dbcoursework.repository;

import com.runtik.dbcoursework.Page;
import com.runtik.dbcoursework.Routines;
import com.runtik.dbcoursework.Tables;
import com.runtik.dbcoursework.dto.EventDTO;
import com.runtik.dbcoursework.tables.pojos.GetEventParticipants;
import com.runtik.dbcoursework.tables.records.EventRecord;
import com.runtik.dbcoursework.tables.records.GetEventParticipantsRecord;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.SelectConditionStep;
import org.jooq.SortField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.runtik.dbcoursework.Routines.addUserOnEvent;

@Repository
public class EventRepository {

    @Autowired
    private DSLContext dslContext;

    public void create(EventDTO event) {
        dslContext.insertInto(
                        Tables.EVENT,
                        Tables.EVENT.EVENT_NAME
                )
                .values(event.getEventName())
                .execute();
    }

    public Page<List<EventDTO>> getEvents(Pageable pageable, List<SortField<?>> sortFields, Condition condition) {
        try (var table = dslContext.selectFrom(Tables.EVENT)) {
            SelectConditionStep<EventRecord> eventRecords =
                    table.where(condition);

            return new Page<>(eventRecords
                    .orderBy(sortFields)
                    .limit(pageable.getPageSize())
                    .offset(pageable.getPageNumber() * pageable.getPageSize())
                    .fetchInto(EventDTO.class),
                    dslContext.fetchCount(eventRecords));
        }
    }

    public void addParticipant(Integer personId, Integer eventId) {
        addUserOnEvent(dslContext.configuration(), personId, eventId);
    }

    public Page<List<GetEventParticipants>> getEventParticipants(
            Integer eventId, Pageable pageable,
            List<SortField<?>> sortFields,
            Condition condition
    ) {
        try (var table = dslContext.selectFrom(Routines.getEventParticipants(eventId))) {
            SelectConditionStep<GetEventParticipantsRecord> getEventParticipantsRecords =
                    table.where(condition);

            return new Page<>(getEventParticipantsRecords
                    .orderBy(sortFields)
                    .limit(pageable.getPageSize())
                    .offset(pageable.getPageNumber() * pageable.getPageSize())
                    .fetchInto(GetEventParticipants.class),
                    dslContext.fetchCount(getEventParticipantsRecords));
        }
    }
}
