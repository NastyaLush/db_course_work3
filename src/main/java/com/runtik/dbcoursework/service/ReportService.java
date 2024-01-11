package com.runtik.dbcoursework.service;

import com.runtik.dbcoursework.dto.ReportDTO;
import com.runtik.dbcoursework.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    private ReportRepository reportRepository;

    public void createReport(ReportDTO report) {
        reportRepository.createReport(report);
    }
    public List<ReportDTO> getReport(int limit, int offset){
        return reportRepository.getReport(limit, offset);
    }

}
