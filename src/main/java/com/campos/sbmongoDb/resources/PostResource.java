package com.campos.sbmongoDb.resources;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

	@RequestMapping(value = "/complexsearch", method = RequestMethod.GET)
	public ResponseEntity<List<Post>> findComplexSearch(
			@RequestParam(value = "keyword", defaultValue = "") String keyword,
		    @RequestParam(value = "startDate") @DateTimeFormat(pattern = "dd/MM/yyyy") Date startDate,
		    @RequestParam(value = "endDate") @DateTimeFormat(pattern = "dd/MM/yyyy") Date endDate,
			@RequestParam(value = "authorname", defaultValue = "") String authorName) {
		
		keyword = URL.decodeParam(keyword);
		authorName = URL.decodeParam(authorName);

		List<Post> list = service.findComplexSearch(keyword, startDate, endDate, authorName);
		return ResponseEntity.ok().body(list);
	}
	
	@RequestMapping(value = "/criteriasearch", method = RequestMethod.GET)
	public ResponseEntity<List<Post>> findCriteriaSearch(
			@RequestParam(value = "keyword", defaultValue = "") String keyword,
			@RequestParam(value = "mindate", defaultValue = "") String minDate,
			@RequestParam(value = "maxdate", defaultValue = "") String maxDate) {
		
		keyword = URL.decodeParam(keyword);
		Date min = URL.convertDate(maxDate, new Date(0));
		Date max = URL.convertDate(maxDate, new Date());
		
		List<Post> list = service.findCriteriaSearch(keyword, min, max);
		return ResponseEntity.ok().body(list);
	}

}
