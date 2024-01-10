package com.runtik.dbcoursework.service;

import com.runtik.dbcoursework.dto.EventDTO;
import com.runtik.dbcoursework.repository.EventRepository;
import com.runtik.dbcoursework.tables.pojos.EventPerson;
import com.runtik.dbcoursework.tables.pojos.GetEventParticipants;
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
    public List<EventDTO> get(){
        return eventRepository.get();
    }
    public void addParticipant(Integer personId, Integer eventId){
        eventRepository.addParticipant(personId, eventId);
    }
    public List<GetEventParticipants> getEventParticipants(Integer eventId){
        return eventRepository.getEventParticipants(eventId);
    }
}
