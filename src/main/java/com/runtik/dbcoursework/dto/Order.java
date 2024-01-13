package com.runtik.dbcoursework.dto;

import lombok.Data;

@Data
public class Order {
    private String field;
    private Direction direction;
    public enum Direction{
        ASC, DESC
    }
}
