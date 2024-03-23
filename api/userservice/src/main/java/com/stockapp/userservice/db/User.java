package com.stockapp.userservice.db;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Document(collection = "users")
public class User {

    @Id
    private UUID id;
    private String name;
    private String email;
    private String password;
    private LocalDateTime birthdate;
}
