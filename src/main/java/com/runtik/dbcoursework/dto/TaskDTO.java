package com.runtik.dbcoursework.dto;

import com.runtik.dbcoursework.enums.Status;
import java.io.Serializable;
import lombok.Getter;

@Getter
public class TaskDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private int taskId;
    private String personNameFrom;
    private String personNameTo;
    private String taskText;
    private Status taskStatus;
    private String taskTitle;
}
