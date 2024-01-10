package com.runtik.dbcoursework.dto;

import java.io.Serializable;
import lombok.Getter;

@Getter
public class ReportDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer reportId;
    private Integer reportCreatedBy;
    private String reportTitle;
    private String reportText;
}
