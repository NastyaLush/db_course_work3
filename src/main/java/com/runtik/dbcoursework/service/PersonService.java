package com.runtik.dbcoursework.service;

import com.runtik.dbcoursework.repository.PersonRepository;
import com.runtik.dbcoursework.tables.pojos.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public void insertPerson(Person person) {
        personRepository.insertPerson(person);
    }

    public List<Person> getPersons() {

        return personRepository.getPersons();
    }
}
