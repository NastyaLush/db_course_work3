package com.runtik.dbcoursework.dto;

import com.runtik.dbcoursework.enums.Status;
import lombok.Data;

@Data
public class TaskCreateDTO {
    private static final long serialVersionUID = 1L;

    private Integer taskPersonTo;
    private Integer taskPersonFrom;
    private String taskText;
    private Status taskStatus;
    private String taskTitle;
}
