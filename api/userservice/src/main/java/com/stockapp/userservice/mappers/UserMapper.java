package com.stockapp.userservice.mappers;

import com.stockapp.userservice.db.User;
import com.stockapp.userservice.models.UserRequest;
import com.stockapp.userservice.models.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User toUser(UserRequest userRequest);

    UserResponse toUserResponse(User user);
}
