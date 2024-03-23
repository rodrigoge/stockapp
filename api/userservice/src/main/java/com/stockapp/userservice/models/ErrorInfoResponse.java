package com.stockapp.userservice.models;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ErrorInfoResponse(String requestID, HttpStatus httpStatus, LocalDateTime dateTime, String message) {
}
