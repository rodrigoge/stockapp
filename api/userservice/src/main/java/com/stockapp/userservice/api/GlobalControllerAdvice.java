package com.stockapp.userservice.api;

import com.stockapp.userservice.exceptions.FlowException;
import com.stockapp.userservice.models.ErrorInfoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(FlowException.class)
    public ResponseEntity<ErrorInfoResponse> handleFlowException(FlowException exception) {
        var errorInfo = new ErrorInfoResponse(
                exception.getRequestID(),
                exception.getHttpStatus(),
                exception.getDateTime(),
                exception.getMessage()
        );
        return ResponseEntity.status(errorInfo.httpStatus().value()).body(errorInfo);
    }
}
