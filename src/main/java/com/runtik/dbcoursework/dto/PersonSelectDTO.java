package com.runtik.dbcoursework.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonSelectDTO {
    private Integer personId;
    private String personName;
    private String personRole;
    private Integer personFractionId;
}
