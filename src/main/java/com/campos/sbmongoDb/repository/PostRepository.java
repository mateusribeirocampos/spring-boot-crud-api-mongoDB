package com.campos.sbmongoDb.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.campos.sbmongoDb.domain.Post;

public interface PostRepository extends MongoRepository<Post, String> {

	// Query method
	List<Post> findByTitleContainingIgnoreCase(String text);
	
	//@Query("{ 'title': { $regex: ?0, $options: 'i' } }")
	//List<Post> searchTitle(String text);

	@Query("{ $and: [" 
			+ "{ $or: [" 
			+ "{ 'title': { $regex: ?0, $options: 'i'} },"
			+ "{ 'body': { $regex: ?0, $options: 'i'} }" 
			+ "] }, " 
			+ "{ 'date': { $gte: ?1, $lte: ?2 } },"
			+ "{ 'author.name': { $regex: ?3, $options: 'i'} },"
			+ "{ 'comments': { $exists: true, $not: { $size: 0 } } }" 
			+ "] }")
	List<Post> findComplexSearch(String keyword, Date startDate, Date endDate, String authorName);
	
	@Query("{ $and: [ { date: { $gte: ?1 } }, { date: { $lte: ?2 } } , { $or: [ { 'title': { $regex: ?0, $options: 'i'} }, { 'body': { $regex: ?1, $options: 'i'} }, { 'comments.text': { $regex: ?2, $options: 'i'} } ] } ] }")
	List<Post> findCriteriaSearch(String keyword, Date minDate, Date maxDate);
	
}
