package com.campos.sbmongoDb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.campos.sbmongoDb.domain.Post;

public interface PostRepository extends MongoRepository<Post, String> {

}
