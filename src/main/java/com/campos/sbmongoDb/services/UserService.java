package com.campos.sbmongoDb.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.campos.sbmongoDb.domain.User;
import com.campos.sbmongoDb.dto.UserCreateDTO;
import com.campos.sbmongoDb.dto.UserDTO;
import com.campos.sbmongoDb.repository.UserRepository;
import com.campos.sbmongoDb.services.exception.ObjectNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository repo;

	public List<User> findAll() {
		return repo.findAll();
	}

	public User findById(String id) {
		Optional<User> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Object not found!"));
	}
	
	public User insert(User obj) {
		return repo.insert(obj);
	}
	
	public User fromDTO(UserDTO objDTO) {
		return new User(objDTO.getId(), objDTO.getName(), objDTO.getEmail(), null);
	}
	
	public User fromDTO(UserCreateDTO objDTO) {
		return new User(null, objDTO.getName(), objDTO.getEmail(), objDTO.getPassword());
	}
	
	public void delete(String id) {
		findById(id);
		repo.deleteById(id);
	}
	
	public User update(User obj) {
		User newObj = findById(obj.getId());
		System.out.println("UserService - User data newObj =" + newObj);
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	private void updateData(User newObj, User obj) {
		newObj.setName(obj.getName());
		newObj.setEmail(obj.getEmail());
		newObj.setPassword(obj.getPassword());
	}
	
	public List<User> findByUserFirstName(String name) {
		return repo.findByNameContainingIgnoreCase(name);
	}
	
	public List<User> findByUserEmail(String email) {
		return repo.findByEmailContainingIgnoreCase(email);
	}
	
	public List<User> findByUserNameOrEmail(String searchTerm) {
		return repo.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(searchTerm, searchTerm);
	}
}
