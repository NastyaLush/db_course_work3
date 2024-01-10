package com.runtik.dbcoursework.dto;

import org.springframework.web.bind.annotation.RequestParam;
import java.io.Serializable;
import lombok.Data;
import lombok.Getter;

@Getter
public class ParticipantDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer personId;
    private Integer eventId;
}
