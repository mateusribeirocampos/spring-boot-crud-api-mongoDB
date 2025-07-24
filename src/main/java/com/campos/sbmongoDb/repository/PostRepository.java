package com.campos.sbmongoDb.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.campos.sbmongoDb.domain.Post;

public interface PostRepository extends MongoRepository<Post, String> {
	
	List<Post> findByTitleContainingIgnoreCase(String text);
	

}
