package com.runtik.dbcoursework.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ArendateDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer placeId;
    private Integer arendatorId;
}
