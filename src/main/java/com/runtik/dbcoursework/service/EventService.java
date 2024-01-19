package com.runtik.dbcoursework.service;

import com.runtik.dbcoursework.Tables;
import com.runtik.dbcoursework.Util;
import com.runtik.dbcoursework.dto.EventDTO;
import com.runtik.dbcoursework.repository.EventRepository;
import com.runtik.dbcoursework.tables.pojos.GetEventParticipants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public void create(EventDTO event) {
        eventRepository.create(event);
    }

    public List<EventDTO> getEvents(Pageable pageable, String[] filter) {
        System.out.println(eventRepository.getEvents(pageable, Util.getSortedFields(pageable.getSort(), Tables.EVENT),
                                                     Util.getFilterFields(filter, Tables.EVENT)).get(0));
        return eventRepository.getEvents(pageable, Util.getSortedFields(pageable.getSort(), Tables.EVENT),
                                         Util.getFilterFields(filter, Tables.EVENT));
    }

    public void addParticipant(Integer personId, Integer eventId) {
        eventRepository.addParticipant(personId, eventId);
    }

    public List<GetEventParticipants> getEventParticipants(Integer eventId, Pageable pageable, String[] filter) {
        return eventRepository.getEventParticipants(eventId, pageable,
                                                    Util.getSortedFields(pageable.getSort(), Tables.EVENT),
                                                    Util.getFilterFields(filter, Tables.EVENT));
    }
}
