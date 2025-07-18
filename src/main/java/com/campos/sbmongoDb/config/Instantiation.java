package com.campos.sbmongoDb.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.campos.sbmongoDb.domain.Post;
import com.campos.sbmongoDb.domain.User;
import com.campos.sbmongoDb.dto.AuthorDTO;
import com.campos.sbmongoDb.repository.PostRepository;
import com.campos.sbmongoDb.repository.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PostRepository postRepo;

	@Override
	public void run(String... args) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		userRepo.deleteAll();
		postRepo.deleteAll();
		
		User maria = new User(null, "Maria Brown", "maria.brown@gmail.com", "123456");
		User alex = new User(null, "Alex Green", "alex.green@gmail.com", "123456");
		User bob = new User(null, "Bob Grey", "bob.grey@gmail.com", "123456");
		
		userRepo.saveAll(Arrays.asList(maria, alex, bob));
		
		Post post1 = new Post(null, sdf.parse("21/03/2018 09:14:12"), "Partiu viagem", "Vou viajar para São Paulo. Abraços!", new AuthorDTO(maria));
		Post post2 = new Post(null, sdf.parse("23/03/2018 06:55:42"), "Bom dia", "Acordei feliz hoje!", new AuthorDTO(maria));
		
		postRepo.saveAll(Arrays.asList(post1, post2));
		
		System.out.println("Dados de teste inseridos com sucesso!");
		System.out.println("Usuários criados: " + userRepo.count());
		System.out.println("Posts criados: " + postRepo.count());
		
		maria.getPosts().addAll(Arrays.asList(post1, post2));
		
		userRepo.save(maria);
		
	}

}
