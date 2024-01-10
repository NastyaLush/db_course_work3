package com.runtik.dbcoursework.repository;

import com.runtik.dbcoursework.Tables;
import com.runtik.dbcoursework.dto.TestResultDTO;
import com.runtik.dbcoursework.tables.records.TestResultRecord;
import org.jooq.DSLContext;
import org.jooq.SelectWhereStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class TestResultRepository {

    @Autowired
    private DSLContext dslContext;

    public List<TestResultDTO> get(Integer id) {
        try (SelectWhereStep<TestResultRecord> table = dslContext.selectFrom(Tables.TEST_RESULT)) {
            return table.where(Tables.TEST_RESULT.TEST_RESULT_ID.eq(id))
                    .fetchInto(TestResultDTO.class);
        }
    }

    public void create(TestResultDTO testResult) {
        dslContext.insertInto(Tables.TEST_RESULT, Tables.TEST_RESULT.PERSON_ID, Tables.TEST_RESULT.FRACTION_ID)
                  .values(testResult.getPersonId(), testResult.getFractionId());
    }
}
