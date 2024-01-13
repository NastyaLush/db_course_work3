package com.runtik.dbcoursework.repository;

import com.runtik.dbcoursework.Tables;
import com.runtik.dbcoursework.dto.TestResultDTO;
import com.runtik.dbcoursework.tables.records.TestResultRecord;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.SelectWhereStep;
import org.jooq.SortField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class TestResultRepository {

    @Autowired
    private DSLContext dslContext;

    public List<TestResultDTO> getTestResults(int limit, int offset, List<SortField<?>> sortFields,
                                              Condition condition) {
        try (SelectWhereStep<TestResultRecord> table = dslContext.selectFrom(Tables.TEST_RESULT)) {
            return table.where(condition).orderBy(sortFields)
                        .limit(limit)
                        .offset(offset)
                        .fetchInto(TestResultDTO.class);
        }
    }

    public void create(TestResultDTO testResult) {
        dslContext.insertInto(Tables.TEST_RESULT, Tables.TEST_RESULT.PERSON_ID, Tables.TEST_RESULT.FRACTION_ID)
                  .values(testResult.getPersonId(), testResult.getFractionId());
    }
}
