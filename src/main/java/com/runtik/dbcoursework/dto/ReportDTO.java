package com.runtik.dbcoursework.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.runtik.dbcoursework.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReportDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer reportId;
    private Integer reportCreatedBy;
    private Integer personToId;
    private String personFrom;
    private String personTo;
    private String reportTitle;
    private String reportText;
    private LocalDateTime createdAt = null;
    private Status reportStatus;
}
