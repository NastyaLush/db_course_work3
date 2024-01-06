package com.runtik.dbcoursework.response;

import com.runtik.dbcoursework.response.Response;
import org.springframework.http.HttpStatus;
import java.util.List;

public class ResponseWithCollection <T> extends Response {
    private final List<T> result;

    public ResponseWithCollection(HttpStatus httpStatus, String message, List result) {
        super(httpStatus, message);
        this.result = result;
    }

    public List<T> getResult() {
        return result;
    }
}
