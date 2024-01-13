package com.runtik.dbcoursework.repository;

import com.runtik.dbcoursework.Tables;
import com.runtik.dbcoursework.dto.ReportDTO;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.SortField;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<ReportDTO> getReport(int limit, int offset, List<SortField<?>> sortFields, Condition condition) {
        try (var table = dslContext.selectFrom(Tables.REPORT)) {
            return table.where(condition).orderBy(sortFields).limit(limit).offset(offset)
                    .fetchInto(ReportDTO.class);
        }
    }
}
