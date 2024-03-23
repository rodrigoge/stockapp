package com.stockapp.userservice.validators;

import com.stockapp.userservice.db.UserRepository;
import com.stockapp.userservice.exceptions.FlowException;
import com.stockapp.userservice.models.UserRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;

@Component
@Log4j2
public class UserValidator {

    @Autowired
    private UserRepository userRepository;

    public void validateUserNullFields(String requestID, UserRequest userRequest) {
        log.info("[RequestID: {}] Validating if any request fields is null.", requestID);
        if (userRequest == null) throw new FlowException(
                requestID,
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now(),
                "[RequestID: " + requestID + "] Error: Request must not be null."
        );
        if (userRequest.name().isEmpty()) throw new FlowException(
                requestID,
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now(),
                "[RequestID: " + requestID + "] Error: Name must not be null."
        );
        if (userRequest.email().isEmpty()) throw new FlowException(
                requestID,
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now(),
                "[RequestID: " + requestID + "] Error: Email must not be null."
        );
        if (userRequest.password().isEmpty()) throw new FlowException(
                requestID,
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now(),
                "[RequestID: " + requestID + "] Error: Password must not be null."
        );
        if (userRequest.birthdate() == null) throw new FlowException(
                requestID,
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now(),
                "[RequestID: " + requestID + "] Error: Birthdate must not be null."
        );
        log.info("[RequestID: {}] Finishing validation if any request fields is null.", requestID);
    }

    public void validateBirthdateIsValid(String requestID, LocalDateTime birthdate) {
        log.info("[RequestID: {}] Validating if birthdate field is valid.", requestID);
        if (birthdate.isAfter(LocalDateTime.now())) throw new FlowException(
                requestID,
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now(),
                "[RequestID: " + requestID + "] Error: Birthdate " + birthdate + " field is not valid."
        );
        log.info("[RequestID: {}] Finishing validation if birthdate field is valid.", requestID);
    }

    public void findUserEmailIsAlreadyExists(String requestID, String email) {
        log.info("[RequestID: {}] Validating if email {} is already exists.", requestID, email);
        var user = userRepository.findByEmail(email);
        if (!ObjectUtils.isEmpty(user)) throw new FlowException(
                requestID,
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now(),
                "[RequestID: " + requestID + "] Error: E-mail " + email + " is already registered."
        );
        log.info("[RequestID: {}] Finishing validation if email is already exists.", requestID);
    }
}
