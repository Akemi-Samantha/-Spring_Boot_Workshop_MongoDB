package com.project.workshop_mongodb.repository;

import com.project.workshop_mongodb.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserRepository extends MongoRepository<User, String> {
}

