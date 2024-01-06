package com.runtik.dbcoursework.response;

import org.springframework.http.HttpStatus;


public class Response {

    private final HttpStatus httpStatus;
    private final String message;

    public Response(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
