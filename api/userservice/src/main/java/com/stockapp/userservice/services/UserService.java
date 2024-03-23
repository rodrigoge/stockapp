package com.stockapp.userservice.services;

import com.stockapp.userservice.db.UserRepository;
import com.stockapp.userservice.mappers.UserMapper;
import com.stockapp.userservice.models.UserRequest;
import com.stockapp.userservice.models.UserResponse;
import com.stockapp.userservice.utils.UserUtils;
import com.stockapp.userservice.validators.UserValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class UserService {

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserUtils userUtils;

    @Autowired
    private UserMapper userMapper;

    public UserResponse createUser(String requestID, UserRequest userRequest) {
        log.info("[RequestID: {}] User trying to register in app.", requestID);
        log.info("[RequestID: {}] Starting validation if request is valid.", requestID);
        userValidator.validateUserNullFields(requestID, userRequest);
        userValidator.validateBirthdateIsValid(requestID, userRequest.birthdate());
        userValidator.findUserEmailIsAlreadyExists(requestID, userRequest.email());
        var modifiedUser = userUtils.encryptPassword(requestID, userRequest);
        log.info("[RequestID: {}] Mapping user request will be saved.", requestID);
        var user = userMapper.toUser(modifiedUser);
        log.info("[RequestID: {}] Saving user {} into database.", requestID, user.getName());
        var userSaved = userRepository.save(user);
        log.info("[RequestID: {}] Mapping user response to returns.", requestID);
        var userResponse = userMapper.toUserResponse(userSaved);
        log.info("[RequestID: {}] Finishing, user {} saved with successfully.", requestID, userResponse.name());
        return userResponse;
    }
}
