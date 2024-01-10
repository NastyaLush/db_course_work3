package com.runtik.dbcoursework.dto;

import com.runtik.dbcoursework.enums.Status;
import java.io.Serializable;
import lombok.Getter;

@Getter
public class TaskDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer taskId;
    private Integer taskPersonTo;
    private Integer taskPersonFrom;
    private String taskText;
    private Status taskStatus;
    private String taskTitle;
}
