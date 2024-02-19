package com.runtik.dbcoursework.repository;

import com.runtik.dbcoursework.Page;
import com.runtik.dbcoursework.Tables;
import com.runtik.dbcoursework.dto.PersonSelectDTO;
import com.runtik.dbcoursework.enums.Role;
import com.runtik.dbcoursework.tables.pojos.Person;
import org.jooq.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PersonRepository {

    @Autowired
    private DSLContext dslContext;


    public PersonSelectDTO createPerson(Person person) {
        return dslContext.insertInto(
                        Tables.PERSON,
                        Tables.PERSON.PERSON_FRACTION_ID,
                        Tables.PERSON.PERSON_NAME,
                        Tables.PERSON.PERSON_PASSWORD,
                        Tables.PERSON.PERSON_ROLE
                )
                .values(
                        person.getPersonFractionId(),
                        person.getPersonName(),
                        person.getPersonPassword(),
                        person.getPersonRole()
                )
                .returningResult(
                        Tables.PERSON.PERSON_ID,
                        Tables.PERSON.PERSON_NAME,
                        Tables.PERSON.PERSON_ROLE,
                        Tables.PERSON.PERSON_FRACTION_ID
                )
                .fetch()
                .into(PersonSelectDTO.class)
                .get(0);
    }

    public Page<List<PersonSelectDTO>> getPersons(
            Pageable pageable,
            List<SortField<?>> sortFields,
            Condition condition
    ) {
        try (var table = dslContext.select(
                        Tables.PERSON.PERSON_ID,
                        Tables.PERSON.PERSON_NAME,
                        Tables.PERSON.PERSON_ROLE,
                        Tables.PERSON.PERSON_FRACTION_ID
                )
                .from(Tables.PERSON)) {
            SelectConditionStep<Record4<Integer, String, Role, Integer>> selectConditionStep =
                    table.where(condition);

            return new Page<>(selectConditionStep
                    .orderBy(sortFields)
                    .limit(pageable.getPageSize())
                    .offset(pageable.getPageNumber())
                    .fetchInto(PersonSelectDTO.class),
                    dslContext.fetchCount(selectConditionStep)
            );
        }
    }

    public Optional<Person> getPersonByName(String name) {
        try (var table = dslContext.selectFrom(Tables.PERSON)) {
            var persons = table.where(
                            Tables.PERSON.PERSON_NAME.eq(name)
                    )
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
            table.where(Tables.PERSON.PERSON_ID.eq(personID));
        }
    }
}
