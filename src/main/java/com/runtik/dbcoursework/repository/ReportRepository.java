package com.runtik.dbcoursework.repository;

import com.runtik.dbcoursework.Page;
import com.runtik.dbcoursework.Tables;
import com.runtik.dbcoursework.dto.ReportDTO;
import com.runtik.dbcoursework.dto.ReportSelectDTO;
import org.jooq.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class ReportRepository {

    @Autowired
    private DSLContext dslContext;

    public void createReport(ReportDTO report) {
        dslContext.insertInto(Tables.REPORT, Tables.REPORT.REPORT_CREATED_BY,
                              Tables.REPORT.REPORT_TITLE,
                              Tables.REPORT.REPORT_TEXT)
                  .values(report.getReportCreatedBy(), report.getReportTitle(), report.getReportTitle())
                  .execute();

    }

    public Page<List<ReportSelectDTO>> getReport(Pageable pageable, List<SortField<?>> sortFields,
                                                 Condition condition) {
        try (var table = dslContext.select(Tables.REPORT.REPORT_ID, Tables.PERSON.PERSON_NAME,
                                           Tables.REPORT.REPORT_TITLE, Tables.REPORT.REPORT_TEXT)
                                   .from(Tables.REPORT)) {
            SelectConditionStep<Record4<Integer, String, String, String>> selectConditionStep = table.join(Tables.PERSON)
                                                                                       .on(Tables.REPORT.REPORT_CREATED_BY.eq(
                                                                                               Tables.PERSON.PERSON_ID))
                                                                                       .where(condition);
            return new Page<>(selectConditionStep
                                   .orderBy(sortFields)
                                   .limit(pageable.getPageSize())
                                   .offset(
                                           pageable.getPageNumber() * pageable.getPageSize())
                                   .fetchInto(ReportSelectDTO.class),
                              dslContext.fetchCount(selectConditionStep)
            );

        }
    }

}
