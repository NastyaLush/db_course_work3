package com.runtik.dbcoursework.dto;

import com.runtik.dbcoursework.enums.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonSelectDTO {
    private Integer       personId;
    private String        personName;
    private Role personRole;
    private Integer       personFractionId;
}
