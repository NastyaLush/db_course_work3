package com.runtik.dbcoursework.dto;

import java.io.Serializable;
import lombok.Getter;

@Getter
public class CharacteristicDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer charectiristicId;
    private String charectiristicName;
}
