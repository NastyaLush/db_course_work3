package com.runtik.dbcoursework.dto;

import com.runtik.dbcoursework.enums.Role;
import java.io.Serializable;
import lombok.Getter;

@Getter
public class PersonDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer personId;
    private String personName;
    private String personPassword;
    private Role personRole;
    private Integer personFractionId;
}
