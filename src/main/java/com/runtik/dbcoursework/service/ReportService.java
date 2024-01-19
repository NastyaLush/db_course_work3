package com.runtik.dbcoursework.service;

import com.runtik.dbcoursework.Tables;
import com.runtik.dbcoursework.Util;
import com.runtik.dbcoursework.dto.ReportDTO;
import com.runtik.dbcoursework.dto.ReportSelectDTO;
import com.runtik.dbcoursework.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    private ReportRepository reportRepository;

    public void createReport(ReportDTO report) {
        reportRepository.createReport(report);
    }
    public List<ReportSelectDTO> getReport(Pageable pageable, String[]filter){
        return reportRepository.getReport(pageable, Util.getSortedFields(pageable.getSort(), Tables.REPORT), Util.getFilterFields(filter, Tables.REPORT));
    }

}
