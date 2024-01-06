package com.runtik.dbcoursework.repository;

import com.runtik.dbcoursework.Tables;
import com.runtik.dbcoursework.tables.pojos.Person;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public class PersonRepository {
    @Autowired
    private DSLContext dslContext;
    public void insertPerson(Person person) {
        dslContext.insertInto(Tables.PERSON, Tables.PERSON.PERSON_FRACTION_ID, Tables.PERSON.PERSON_NAME,
                              Tables.PERSON.PERSON_ROLE)
                  .values(person.getPersonFractionId(), person.getPersonName(), person.getPersonRole())
                  .execute();
    }

    public List<Person> getPersons() {

        return dslContext.selectFrom(Tables.PERSON)
                         .fetchInto(Person.class);
    }
    public Person getPersonByName(String name) {
        var persons = dslContext.selectFrom(Tables.PERSON).where(Tables.PERSON.PERSON_NAME.eq(name))
                                .fetchInto(Person.class);
        if(persons.size()!=1){
            return null;
        }
        return persons.get(0);
    }
}
