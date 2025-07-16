package com.campos.sbmongo.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.campos.sbmongoDb.domain.User;
import com.campos.sbmongoDb.repository.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner {
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public void run(String... args) throws Exception {
		
		userRepo.deleteAll();
		
		User maria = new User(null, "Maria Brown", "maria.brown@gmail.com");
		User alex = new User(null, "Alex Green", "alex.green@gmail.com");
		User bob = new User(null, "Bob Grey", "bob.grey@gmail.com");
		
		userRepo.saveAll(Arrays.asList(maria, alex, bob));
	}

	
}
