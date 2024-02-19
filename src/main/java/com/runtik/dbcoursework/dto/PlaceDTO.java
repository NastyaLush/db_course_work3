package com.runtik.dbcoursework.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlaceDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer placeId;
    private String fractionName;
    private String placeName;
    private Boolean booked;
}
