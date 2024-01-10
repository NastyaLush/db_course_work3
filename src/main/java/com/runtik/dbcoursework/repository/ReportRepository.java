package com.runtik.dbcoursework.repository;

import com.runtik.dbcoursework.Tables;
import com.runtik.dbcoursework.dto.ReportDTO;
import org.jooq.DSLContext;
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

    public List<ReportDTO> get() {
        try (var table = dslContext.selectFrom(Tables.REPORT)) {
            return table
                    .fetchInto(ReportDTO.class);
        }
    }
}
