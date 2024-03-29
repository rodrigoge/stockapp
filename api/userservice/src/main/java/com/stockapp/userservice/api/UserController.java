package com.stockapp.userservice.api;

import com.stockapp.userservice.models.LoginRequest;
import com.stockapp.userservice.models.LoginResponse;
import com.stockapp.userservice.models.UserRequest;
import com.stockapp.userservice.models.UserResponse;
import com.stockapp.userservice.services.LoginService;
import com.stockapp.userservice.services.UserService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginService loginService;

    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(@RequestHeader String requestID,
                                                   @Valid @RequestBody UserRequest userRequest) {
        log.info("[RequestID: {}] Starting the create new user.", requestID);
        var userResponse = userService.createUser(requestID, userRequest);
        log.info("[RequestID: {}] Finishing the create new user.", requestID);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestHeader String requestID,
                                               @Valid @RequestBody LoginRequest loginRequest) {
        log.info("[RequestID: {}] Starting the login user flow.", requestID);
        var loginResponse = loginService.login(requestID, loginRequest);
        log.info("[RequestID: {}] Finishing the login user flow.", requestID);
        return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
    }
}
