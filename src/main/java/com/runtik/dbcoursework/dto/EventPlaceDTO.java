package com.runtik.dbcoursework.dto;

import java.io.Serializable;
import lombok.Getter;

@Getter
public class EventPlaceDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer eventPlaceId;
    private Integer eventId;
    private Integer placeArendatorId;
}
