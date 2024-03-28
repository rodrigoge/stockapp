package com.stockapp.userservice.services;

import com.stockapp.userservice.db.User;
import com.stockapp.userservice.models.LoginRequest;
import com.stockapp.userservice.models.LoginResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public LoginResponse login(String requestID, LoginRequest loginRequest) {
        log.info("[RequestID: {}] User trying to login in app.", requestID);
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password());
        log.info("[RequestID: {}] Send request to authentication.", requestID);
        var authentication = authenticationManager.authenticate(usernamePassword);
        log.info("[RequestID: {}] Generating token to login in app.", requestID);
        var token = tokenService.generateToken(requestID, (User) authentication);
        var loginResponse = new LoginResponse(authentication.getName(), token);
        log.info("[RequestID: {}] Finishing the login flow.", requestID);
        return loginResponse;
    }
}
