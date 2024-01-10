package com.runtik.dbcoursework.service;

import com.runtik.dbcoursework.dto.ReportDTO;
import com.runtik.dbcoursework.repository.PersonRepository;
import com.runtik.dbcoursework.repository.ReportRepository;
import com.runtik.dbcoursework.tables.pojos.Person;
import com.runtik.dbcoursework.tables.pojos.Report;
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
    public List<ReportDTO> get(){
        return reportRepository.get();
    }

}
