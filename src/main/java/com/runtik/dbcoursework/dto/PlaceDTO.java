package com.runtik.dbcoursework.dto;

import java.io.Serializable;
import lombok.Getter;

@Getter
public class PlaceDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer placeId;
    private Integer fractionId;
    private String placeName;
}
