package com.project.workshop_mongodb.repository;

import com.project.workshop_mongodb.domain.Post;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface PostRepository extends MongoRepository<Post, String> {
}

