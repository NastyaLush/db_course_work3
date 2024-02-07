package com.runtik.dbcoursework;

import java.io.Serializable;
import lombok.Data;

@Data
public class Page<T> implements Serializable {
    private final T result;
    private final int countOfRows;
}
