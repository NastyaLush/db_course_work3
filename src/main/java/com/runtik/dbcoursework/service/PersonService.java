package com.runtik.dbcoursework.service;

import com.runtik.dbcoursework.Tables;
import com.runtik.dbcoursework.Util;
import com.runtik.dbcoursework.dto.PersonCreateDTO;
import com.runtik.dbcoursework.dto.PersonSelectDTO;
import com.runtik.dbcoursework.enums.Role;
import com.runtik.dbcoursework.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;


    public void changePersonRole(Integer personID, Role role){
        personRepository.changePersonRole(personID, role);
    }
    public List<PersonSelectDTO> getPersons(Pageable pageable, String[]filter) {

        return personRepository.getPersons(pageable, Util.getSortedFields(pageable.getSort(), Tables.PERSON), Util.getFilterFields(filter, Tables.PERSON));

    }
}
