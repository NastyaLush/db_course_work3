package com.runtik.dbcoursework.repository;

import com.runtik.dbcoursework.Page;
import com.runtik.dbcoursework.Tables;
import com.runtik.dbcoursework.dto.ReportDTO;
import com.runtik.dbcoursework.dto.ReportSelectDTO;
import com.runtik.dbcoursework.enums.Status;
import org.jooq.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class ReportRepository {

    private final DSLContext dslContext;

    public ReportRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public void createReport(ReportDTO report) {
        dslContext.insertInto(
                        Tables.REPORT,
                        Tables.REPORT.REPORT_CREATED_BY,
                        Tables.REPORT.PERSON_TO,
                        Tables.REPORT.REPORT_TITLE,
                        Tables.REPORT.REPORT_TEXT
                )
                .values(
                        report.getReportCreatedBy(),
                        report.getPersonToId(),
                        report.getReportTitle(),
                        report.getReportText()
                )
                .execute();

    }

    public Page<List<ReportSelectDTO>> getReport(
            Pageable pageable,
            List<SortField<?>> sortFields,
            Condition condition
    ) {
        try (var table = dslContext.select(
                        Tables.REPORT.REPORT_ID,
                        Tables.PERSON.PERSON_NAME,
                        Tables.REPORT.REPORT_TITLE,
                        Tables.REPORT.REPORT_TEXT)
                .from(Tables.REPORT)
        ) {
            SelectConditionStep<Record4<Integer, String, String, String>> selectConditionStep =
                    table.join(Tables.PERSON)
                            .on(Tables.REPORT.REPORT_CREATED_BY.eq(Tables.PERSON.PERSON_ID))
                            .where(condition);

            return new Page<>(
                    selectConditionStep
                            .orderBy(Tables.REPORT.REPORT_ID.desc())
                            .limit(pageable.getPageSize())
                            .offset(pageable.getPageNumber() * pageable.getPageSize())
                            .fetchInto(ReportSelectDTO.class),
                    dslContext.fetchCount(selectConditionStep)
            );

        }
    }

    public ReportSelectDTO getById(int id) {
        try (var table = dslContext.select(
                        Tables.REPORT.REPORT_ID,
                        Tables.PERSON.PERSON_NAME,
                        Tables.REPORT.REPORT_TITLE,
                        Tables.REPORT.REPORT_TEXT,
                        Tables.REPORT.REPORT_STATUS
                )
                .from(Tables.REPORT)) {
            return table.join(Tables.PERSON)
                    .on(Tables.REPORT.REPORT_CREATED_BY.eq(Tables.PERSON.PERSON_ID))
                    .where(Tables.REPORT.REPORT_ID.eq(id))
                    .fetchInto(ReportSelectDTO.class)
                    .get(0);

        }
    }

    public Optional<List<ReportDTO>> getNewReports(int userId) {
        try (var q = dslContext.select(
                        Tables.REPORT.REPORT_ID,
                        Tables.REPORT.REPORT_CREATED_BY,
                        Tables.REPORT.PERSON_TO,
                        Tables.PERSON.as("p1").PERSON_NAME.as("personFrom"),
                        Tables.PERSON.as("p2").PERSON_NAME.as("personTo"),
                        Tables.REPORT.REPORT_TITLE,
                        Tables.REPORT.REPORT_TEXT,
                        Tables.REPORT.CREATED_AT,
                        Tables.REPORT.REPORT_STATUS
                )
                .from(Tables.REPORT)
        ) {
            var res = q.join(Tables.PERSON.as("p1"))
                    .on(Tables.REPORT.REPORT_CREATED_BY.eq(Tables.PERSON.as("p1").PERSON_ID))
                    .join(Tables.PERSON.as("p2"))
                    .on(Tables.REPORT.PERSON_TO.eq(Tables.PERSON.as("p2").PERSON_ID))
                    .where(Tables.REPORT.PERSON_TO.eq(userId),
                            Tables.REPORT.REPORT_STATUS.eq(Status.created)
                                    .or(Tables.REPORT.REPORT_STATUS.eq(Status.need_check))
                    )
                    .orderBy(Tables.REPORT.CREATED_AT.desc())
                    .limit(10)
                    .fetchInto(ReportDTO.class);

            if (res.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(res);
        }
    }

    public void updateStatus(Integer id, Status newStatus) {
        try (var table = dslContext.update(Tables.REPORT)
                .set(Tables.REPORT.REPORT_STATUS, newStatus)) {
            table
                    .where(Tables.REPORT.REPORT_ID.eq(id))
                    .execute();
        }
    }

}
