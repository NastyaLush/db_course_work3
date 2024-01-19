package com.runtik.dbcoursework.repository;

import com.runtik.dbcoursework.Tables;
import com.runtik.dbcoursework.dto.TestResultDTO;
import com.runtik.dbcoursework.dto.TestResultSelectDTO;
import org.jooq.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class TestResultRepository {

    @Autowired
    private DSLContext dslContext;

    public List<TestResultSelectDTO> getTestResults(Pageable pageable, List<SortField<?>> sortFields,
                                              Condition condition) {
        try (SelectJoinStep<Record2<String, String>> table = dslContext.select(Tables.PERSON.PERSON_NAME, Tables.FRACTION.FRACTION_NAME)
                                                                                                          .from(Tables.TEST_RESULT)) {
            return table.join(Tables.FRACTION)
                        .on(Tables.TEST_RESULT.FRACTION_ID.eq(Tables.FRACTION.FRACTION_ID))
                        .join(Tables.PERSON)
                        .on(Tables.TEST_RESULT.PERSON_ID.eq(Tables.PERSON.PERSON_ID))
                        .where(condition)
                        .orderBy(sortFields)
                        .limit(pageable.getPageSize())
                        .offset(pageable.getPageNumber() * pageable.getPageSize())
                        .fetchInto(TestResultSelectDTO.class);
        }
    }

    public void create(TestResultDTO testResult) {
        dslContext.insertInto(Tables.TEST_RESULT, Tables.TEST_RESULT.PERSON_ID, Tables.TEST_RESULT.FRACTION_ID)
                  .values(testResult.getPersonId(), testResult.getFractionId());
    }
}
