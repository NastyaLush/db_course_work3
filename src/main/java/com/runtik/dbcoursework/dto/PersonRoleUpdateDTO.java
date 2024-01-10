package com.runtik.dbcoursework.dto;

import com.runtik.dbcoursework.enums.Role;
import java.io.Serializable;
import lombok.Getter;

@Getter
public class PersonRoleUpdateDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer personID;
    private Role role;
}
