package com.runtik.dbcoursework.repository;

import com.runtik.dbcoursework.Tables;
import com.runtik.dbcoursework.dto.EventDTO;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
import static com.runtik.dbcoursework.Routines.addUserOnEvent;

@Repository
public class EventRepository {

    @Autowired
    private DSLContext dslContext;

    public void create(EventDTO event) {
        dslContext.insertInto(Tables.EVENT, Tables.EVENT.EVENT_NAME)
                  .values(event.getEventName())
                  .execute();
    }

    public List<EventDTO> getEvents(int limit, int offset) {
        try (var table = dslContext.selectFrom(Tables.EVENT)) {
            return table.limit(limit).offset(offset)
                    .fetchInto(EventDTO.class);
        }
    }

    public void addParticipant(Integer personId, Integer eventId) {
        addUserOnEvent(dslContext.configuration(), personId, eventId);
    }

    public List<EventDTO> getEventParticipants(Integer eventId, int limit, int offset) {
        try (var table = dslContext.selectFrom(Tables.EVENT)) {
            return table.where(Tables.EVENT.EVENT_ID.eq(eventId)).limit(limit).offset(offset)
                        .fetchInto(EventDTO.class);
        }
    }

}
