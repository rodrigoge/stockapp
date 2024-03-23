package com.stockapp.userservice.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class FlowException extends RuntimeException {

    private final String requestID;
    private final HttpStatus httpStatus;
    private final LocalDateTime dateTime;
    private final String message;

    public FlowException(String requestID, HttpStatus httpStatus, LocalDateTime dateTime, String message) {
        super(message);
        this.requestID = requestID;
        this.httpStatus = httpStatus;
        this.dateTime = dateTime;
        this.message = message;
    }
}
