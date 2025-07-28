package com.campos.sbmongoDb.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.campos.sbmongoDb.domain.Post;
import com.campos.sbmongoDb.domain.User;
import com.campos.sbmongoDb.dto.UserCreateDTO;
import com.campos.sbmongoDb.dto.UserDTO;
import com.campos.sbmongoDb.resources.util.URL;
import com.campos.sbmongoDb.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

	@Autowired
	private UserService service;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<UserDTO>> findAll() {
		List<User> list = service.findAll();
		List<UserDTO> listDTO = list.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<UserDTO> findById(@PathVariable String id) {
		User obj = service.findById(id);
		return ResponseEntity.ok().body(new UserDTO(obj));
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody UserCreateDTO objDTO) {
		User obj = service.fromDTO(objDTO);
		System.out.println(obj);
		obj = service.insert(obj);
		System.out.println(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(obj.getId()).toUri();
		System.out.println(uri);
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody UserCreateDTO objDto, @PathVariable String id) {
		User obj = service.fromDTO(objDto);
		System.out.println("UserResource - New data from updated obj =" + obj);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}/posts", method = RequestMethod.GET)
	public ResponseEntity<List<Post>> findPosts(@PathVariable String id) {
		User obj = service.findById(id);
		return ResponseEntity.ok().body(obj.getPosts());
	}

	@RequestMapping(value = "/namesearch", method = RequestMethod.GET)
	public ResponseEntity<List<UserDTO>> findByUserFirstName(
			@RequestParam(value = "text", defaultValue = "") String text) {
		text = URL.decodeParam(text);
		// user list by name
		List<User> users = service.findByUserFirstName(text);
		System.out.println(text);

		// userDTO list from user list
		List<UserDTO> userDTO = users.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
		System.out.println(userDTO);
		return ResponseEntity.ok().body(userDTO);
	}

	@RequestMapping(value = "/emailsearch", method = RequestMethod.GET)
	public ResponseEntity<List<UserDTO>> findByUserEmail(@RequestParam(value = "text", defaultValue = "") String text) {
		List<User> users = service.findByUserEmail(text);
		System.out.println(text);
		List<UserDTO> userDTO = users.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
		System.out.println(userDTO);
		return ResponseEntity.ok().body(userDTO);
	}

	@RequestMapping(value = "/nameoremailsearch", method = RequestMethod.GET)
	public ResponseEntity<List<UserDTO>> findByUserNameOrEmail(
			@RequestParam(value = "text", defaultValue = "") String text) {
		List<User> users = service.findByUserNameOrEmail(text);
		System.out.println("Text: " + text);
		List<UserDTO> userDTO = users.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
		System.out.println(userDTO);
		return ResponseEntity.ok().body(userDTO);
	}
}
