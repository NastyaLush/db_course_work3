package com.runtik.dbcoursework.dto;

import java.io.Serializable;
import lombok.Getter;

@Getter
public class TestResultDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer testResultId;
    private Integer personId;
    private Integer fractionId;
}
