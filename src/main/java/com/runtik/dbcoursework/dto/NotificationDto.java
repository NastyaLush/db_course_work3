package com.runtik.dbcoursework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;


@Data
@AllArgsConstructor
public class NotificationDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Optional<List<ReportDTO>> reports;
    private Optional<List<TaskDTO>> tasks;
}
