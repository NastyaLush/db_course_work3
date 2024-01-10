package com.runtik.dbcoursework.service;

import com.runtik.dbcoursework.dto.PersonDTO;
import com.runtik.dbcoursework.enums.Role;
import com.runtik.dbcoursework.repository.PersonRepository;
import com.runtik.dbcoursework.tables.pojos.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public void insertPerson(PersonDTO person) {
        personRepository.createPerson(person);
    }

    public void changePersonRole(Integer personID, Role role){
        personRepository.changePersonRole(personID, role);
    }
    public List<PersonDTO> getPersons() {

        return personRepository.getPersons();
    }
}
