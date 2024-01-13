package com.runtik.dbcoursework.repository;

import com.runtik.dbcoursework.Tables;
import com.runtik.dbcoursework.dto.PersonCreateDTO;
import com.runtik.dbcoursework.enums.Role;
import com.runtik.dbcoursework.tables.pojos.Person;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.SortField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class PersonRepository {

    @Autowired
    private DSLContext dslContext;


    public void createPerson(Person person) {
        dslContext.insertInto(Tables.PERSON, Tables.PERSON.PERSON_FRACTION_ID, Tables.PERSON.PERSON_NAME,
                              Tables.PERSON.PERSON_PASSWORD,
                              Tables.PERSON.PERSON_ROLE)
                  .values(person.getPersonFractionId(), person.getPersonName(), person.getPersonPassword(), person.getPersonRole())
                  .execute();
    }

    public List<PersonCreateDTO> getPersons(int limit, int offset, List<SortField<?>> sortFields, Condition condition) {
        try (var table = dslContext.selectFrom(Tables.PERSON)) {
            return table.where(condition).orderBy(sortFields).limit(limit).offset(offset)
                    .fetchInto(PersonCreateDTO.class);
        }
    }

    public Optional<Person> getPersonByName(String name) {
        try (var table = dslContext.selectFrom(Tables.PERSON)) {
            var persons = table.where(Tables.PERSON.PERSON_NAME.eq(name))
                               .fetchInto(Person.class);
            if (persons.size() != 1) {
                return Optional.empty();
            }
            return Optional.of(persons.get(0));
        }

    }

    public void changePersonRole(Integer personID, Role role) {
        try (var table = dslContext.update(Tables.PERSON)
                                   .set(Tables.PERSON.PERSON_ROLE, role)) {
            table
                    .where(Tables.PERSON.PERSON_ID.eq(personID));
        }
    }
}
