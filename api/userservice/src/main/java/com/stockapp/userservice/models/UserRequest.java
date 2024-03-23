package com.stockapp.userservice.models;

import java.time.LocalDateTime;

public record UserRequest(String name, String password, String email, LocalDateTime birthdate) {
}
