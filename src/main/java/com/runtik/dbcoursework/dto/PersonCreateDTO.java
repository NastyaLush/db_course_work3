package com.runtik.dbcoursework.dto;

import com.runtik.dbcoursework.enums.Role;
import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonCreateDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String personName;
    private String personPassword;
    private Role personRole;
    private Integer personFractionId;
}
