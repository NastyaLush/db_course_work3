package com.runtik.dbcoursework.service;

import com.runtik.dbcoursework.Tables;
import com.runtik.dbcoursework.Util;
import com.runtik.dbcoursework.dto.EventDTO;
import com.runtik.dbcoursework.dto.Order;
import com.runtik.dbcoursework.repository.EventRepository;
import com.runtik.dbcoursework.tables.pojos.GetEventParticipants;
import org.jooq.SortField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public void create(EventDTO event){
        eventRepository.create(event);
    }
    public List<EventDTO> getEvents(int limit, int offset, String[] sortFields,String[]filter){
        return eventRepository.getEvents(limit, offset, Util.getSortedFields(sortFields, Tables.EVENT), Util.getFilterFields(filter, Tables.EVENT));
    }
    public void addParticipant(Integer personId, Integer eventId){
        eventRepository.addParticipant(personId, eventId);
    }
    public List<GetEventParticipants> getEventParticipants(Integer eventId, int limit, int offset, String[] sortFields,String[]filter){
        return eventRepository.getEventParticipants(eventId,limit,offset,Util.getSortedFields(sortFields, Tables.EVENT), Util.getFilterFields(filter, Tables.EVENT));
    }
}
