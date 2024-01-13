package com.runtik.dbcoursework.service;

import com.runtik.dbcoursework.Tables;
import com.runtik.dbcoursework.Util;
import com.runtik.dbcoursework.dto.PersonCreateDTO;
import com.runtik.dbcoursework.enums.Role;
import com.runtik.dbcoursework.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;


    public void changePersonRole(Integer personID, Role role){
        personRepository.changePersonRole(personID, role);
    }
    public List<PersonCreateDTO> getPersons(int limit, int offset, String[] sortFields,String[]filter) {

        return personRepository.getPersons(limit, offset, Util.getSortedFields(sortFields, Tables.PERSON), Util.getFilterFields(filter, Tables.PERSON));
    }
}
