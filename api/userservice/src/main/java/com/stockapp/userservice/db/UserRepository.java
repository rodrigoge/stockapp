package com.stockapp.userservice.db;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface UserRepository extends MongoRepository<User, UUID> {

    User findByEmail(String email);
}
