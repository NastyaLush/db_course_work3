package com.runtik.dbcoursework.dto;

import lombok.Data;

@Data
public class ReportSelectDTO {
    private static final long serialVersionUID = 1L;

    private Integer reportId;
    private String personName;
    private String reportTitle;
    private String reportText;
}
