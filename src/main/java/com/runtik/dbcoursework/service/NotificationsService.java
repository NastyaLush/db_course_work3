package com.runtik.dbcoursework.service;

import com.runtik.dbcoursework.dto.NotificationDto;
import com.runtik.dbcoursework.repository.ReportRepository;
import com.runtik.dbcoursework.repository.TaskRepository;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

@Service
public class NotificationsService {

    private final ReportRepository reportRepository;
    private final TaskRepository taskRepository;

    public NotificationsService(ReportRepository reportRepository, TaskRepository taskRepository) {
        this.reportRepository = reportRepository;
        this.taskRepository = taskRepository;

    }

    public NotificationDto getNewNotifications(int userId) {
        var reports = this.reportRepository.getNewReports(userId);
        var tasks = this.taskRepository.getNewTasks(userId);

        return new NotificationDto(reports, tasks);
    }

}
