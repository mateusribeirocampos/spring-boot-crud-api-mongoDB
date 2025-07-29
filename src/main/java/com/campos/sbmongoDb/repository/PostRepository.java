package com.campos.sbmongoDb.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.campos.sbmongoDb.domain.Post;

public interface PostRepository extends MongoRepository<Post, String> {
	
	@Query("{ 'title': { $regex: ?0, $options: 'i' } }")
	List<Post> searchTitle(String text);
	
	//List<Post> findByTitleContainingIgnoreCase(String text);
	
	@Query("" +
			"{ $and: [ " +
			"{ 'title': { $regex: ?0, $options: 'i' } }, " +
			"{ 'body': { $regex: ?1, $options: 'i' } }, " +
			"] }")
	List<Post> searchTitleBody(String keyword);
	
	@Query("" +
			"{ $and: [ " +
			"{ 'author': { $regex: ?0, $options: 'i' } }, " +
			"{ 'comments': { $regex: ?1, $options: 'i' } }, " +
			"] }")
	List<Post> searchAuthorNameComments(String authorName, String comments);
	
	@Query("{ 'date': { $gte: ?1, $lte: ?2 } }")
	List<Post> searchDateRange(Date startDate, Date endDate);
}
