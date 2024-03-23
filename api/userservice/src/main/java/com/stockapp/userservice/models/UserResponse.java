package com.stockapp.userservice.models;

import java.time.LocalDateTime;

public record UserResponse(String name, String email, LocalDateTime birthdate) {
}
