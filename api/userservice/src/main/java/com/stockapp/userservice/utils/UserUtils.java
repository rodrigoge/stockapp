package com.stockapp.userservice.utils;

import com.stockapp.userservice.models.UserRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class UserUtils {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserRequest encryptPassword(String requestID, UserRequest userRequest) {
        log.info("[RequestID: {}] Initializing encrypting password.", requestID);
        var encryptedPassword = passwordEncoder.encode(userRequest.password());
        var modifiedUser = new UserRequest(
                userRequest.name(),
                userRequest.email(),
                encryptedPassword,
                userRequest.birthdate()
        );
        log.info("[RequestID: {}] Finishing encrypting password.", requestID);
        return modifiedUser;
    }
}
