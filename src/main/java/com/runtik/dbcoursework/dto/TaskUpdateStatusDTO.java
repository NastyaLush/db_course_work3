package com.runtik.dbcoursework.dto;

import com.runtik.dbcoursework.enums.Status;
import java.io.Serializable;
import lombok.Getter;

@Getter
public class TaskUpdateStatusDTO implements Serializable {
        private static final long serialVersionUID = 1L;
        private Integer id;
        private Status newStatus;

}
