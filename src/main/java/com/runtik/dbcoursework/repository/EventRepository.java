package com.runtik.dbcoursework.repository;

import com.runtik.dbcoursework.Tables;
import com.runtik.dbcoursework.dto.EventDTO;
import com.runtik.dbcoursework.tables.pojos.EventPerson;
import com.runtik.dbcoursework.tables.pojos.GetEventParticipants;
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

    public List<EventDTO> get() {
        try (var table = dslContext.selectFrom(Tables.EVENT)) {
            return table
                    .fetchInto(EventDTO.class);
        }
    }

    public void addParticipant(Integer personId, Integer eventId) {
        addUserOnEvent(dslContext.configuration(), personId, eventId);
    }

    public List<GetEventParticipants> getEventParticipants(Integer eventId) {
        return Tables.GET_EVENT_PARTICIPANTS(dslContext.configuration(), eventId)
                     .into(GetEventParticipants.class);
    }

}
