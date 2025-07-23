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
import com.campos.sbmongoDb.dto.CommentDTO;
import com.campos.sbmongoDb.repository.PostRepository;
import com.campos.sbmongoDb.repository.UserRepository;
import com.campos.sbmongoDb.util.DateValidator;

@Configuration
public class Instantiation implements CommandLineRunner {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PostRepository postRepo;

	@Override
	public void run(String... args) throws Exception {
		
		//SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		//sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		userRepo.deleteAll();
		postRepo.deleteAll();
		
		User maria = new User(null, "Maria Brown", "maria.brown@gmail.com", "123456");
		User alex = new User(null, "Alex Green", "alex.green@gmail.com", "123456");
		User bob = new User(null, "Bob Grey", "bob.grey@gmail.com", "123456");
		
		userRepo.saveAll(Arrays.asList(maria, alex, bob));
		System.out.println("User created with sucess!");
		
		Post post1 = new Post(null, DateValidator.parseDate("21/03/2018 11:23:23"), "Partiu viagem", "Vou viajar para São Paulo. Abraços!", new AuthorDTO(maria));
		Post post2 = new Post(null, DateValidator.parseDate("23/03/2018 07:02:23"), "Bom dia", "Acordei feliz hoje!", new AuthorDTO(maria));
		System.out.println("Post created with sucess!");
		
		CommentDTO c1 = new CommentDTO("Boa viagem mano!", DateValidator.parseDate("21-03-2018"), new AuthorDTO(alex));
		CommentDTO c2 = new CommentDTO("Aproveite!", DateValidator.parseDate("22/03/2018"), new AuthorDTO(bob));
		CommentDTO c3 = new CommentDTO("Tenha um otimo dia!", DateValidator.parseDate("23/03/2018"), new AuthorDTO(alex));
		System.out.println("Comments created with sucess!");
		
		post1.getComments().addAll(Arrays.asList(c1, c2));
		post2.getComments().addAll(Arrays.asList(c3));
		
		postRepo.saveAll(Arrays.asList(post1, post2));
		
		System.out.println("Dados de teste inseridos com sucesso!");
		System.out.println("Usuários criados: " + userRepo.count());
		System.out.println("Posts criados: " + postRepo.count());
		System.out.println("Comments no post1: " + post1.getComments().size());
		System.out.println("Comments no post2: " + post2.getComments().size());
		
		maria.getPosts().addAll(Arrays.asList(post1, post2));
		
		userRepo.save(maria);
		
	}

}
