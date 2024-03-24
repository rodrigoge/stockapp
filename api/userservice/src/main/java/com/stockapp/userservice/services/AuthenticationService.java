package com.stockapp.userservice.services;

import com.stockapp.userservice.db.UserRepository;
import com.stockapp.userservice.exceptions.FlowException;
import com.stockapp.userservice.models.AuthenticationResponse;
import com.stockapp.userservice.models.LoginRequest;
import com.stockapp.userservice.models.LogoutRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Log4j2
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthenticationResponse login(String requestID, LoginRequest loginRequest) {
        log.info("[RequestID: {}] User {} is trying to login.", requestID, loginRequest.email());
        var authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password());
        log.info("[RequestID: {}] Authenticate user in login flow.", requestID);
        authenticationManager.authenticate(authenticationToken);
        log.info("[RequestID: {}] Finding user by email {}.", requestID, loginRequest.email());
        var user = userRepository.findByEmail(loginRequest.email());
        if (user == null) throw new FlowException(
                requestID,
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now(),
                "[RequestID: " + requestID + "] Error: User not found."
        );
        log.info("[RequestID: {}] Generating token by user email.", requestID);
        var jwtToken = jwtService.generateToken(user);
        log.info("[RequestID: {}] Returning toke authentication.", requestID);
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse logout(String requestID, LogoutRequest logoutRequest) {
        log.info("[RequestID: {}] User {} is trying to logout.", requestID, logoutRequest.email());
        var user = User
                .builder()
                .username(logoutRequest.email())
                .build();
        log.info("[RequestID: {}] Generating token by user email.", requestID);
        var jwt = jwtService.generateToken(user);
        return new AuthenticationResponse(jwt);
    }
}
