package com.campos.sbmongoDb.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.campos.sbmongoDb.domain.Post;
import com.campos.sbmongoDb.repository.PostRepository;
import com.campos.sbmongoDb.services.exception.ObjectNotFoundException;

@Service
public class PostService {

	@Autowired
	private PostRepository postRepo;

	public Post findById(String id) {
		Optional<Post> postObj = postRepo.findById(id);
		return postObj.orElseThrow(() -> new ObjectNotFoundException("Object not found!"));
	}
	
	public List<Post> findByTitle(String text) {
		return postRepo.searchTitle(text);
	}
	
	public List<Post> findByTitleBody(String keyword) {
		return postRepo.searchTitleBody(keyword);
	}
	
	public List<Post> findByAuthorComments(String authorName, String comments) {
		return postRepo.searchAuthorNameComments(authorName, comments);
	}
	
	public List<Post> findByDateRange(Date startDate, Date endDate) {
		return postRepo.searchDateRange(startDate, endDate);
	}
}
