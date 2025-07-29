package com.campos.sbmongoDb.resources;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.campos.sbmongoDb.domain.Post;
import com.campos.sbmongoDb.resources.util.URL;
import com.campos.sbmongoDb.services.PostService;

@RestController
@RequestMapping(value = "/posts")
public class PostResource {

	@Autowired
	private PostService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Post> findById(@PathVariable String id) {
		Post postObj = service.findById(id);
		return ResponseEntity.ok().body(postObj);
	}
	
	@RequestMapping(value = "/titlesearch", method = RequestMethod.GET)
	public ResponseEntity<List<Post>> findByTitle(@RequestParam(value = "text", defaultValue = "") String text) {
		text = URL.decodeParam(text);
		List<Post> list = service.findByTitle(text);
		return ResponseEntity.ok().body(list);
	}
	
	@RequestMapping(value = "/titlebodysearch", method = RequestMethod.GET)
	public ResponseEntity<List<Post>> findByTitleBodyDateAuthor(@RequestParam(value = "text", defaultValue = "") String keyword) {
		keyword = URL.decodeParam(keyword);
		List<Post> list = service.findByTitleBody(keyword);
		return ResponseEntity.ok().body(list);
	}
	
	@RequestMapping(value = "/authorcommentsearch", method = RequestMethod.GET)
	public ResponseEntity<List<Post>> findByAuthorComments(@RequestParam(value = "text", defaultValue = "") String authorName, String comments) {
		authorName = URL.decodeParam(authorName);
		comments = URL.decodeParam(comments);
		List<Post> list = service.findByAuthorComments(authorName, comments);
		return ResponseEntity.ok().body(list);
	}
	
	@RequestMapping(value = "/daterangesearch", method = RequestMethod.GET)
	public ResponseEntity<List<Post>> findByDateRange(@RequestParam(value = "text", defaultValue = "") Date startDate, Date endDate) {
		List<Post> list = service.findByDateRange(startDate, endDate);
		return ResponseEntity.ok().body(list);
	}

}
