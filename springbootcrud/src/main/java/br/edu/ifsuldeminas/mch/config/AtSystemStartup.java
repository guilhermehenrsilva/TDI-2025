package br.edu.ifsuldeminas.mch.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Component;

import br.edu.ifsuldeminas.mch.springbootcrud.model.Repository.UserRepository;
import jakarta.transaction.Transactional;

@Component
@Transactional
public class AtSystemStartup implements CommandLineRunner {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		User guilherme = new User(null);
		guilherme.setName("Guilherme Hnerique");
		guilherme.setGender(User.Gender.M);
		guilherme.setEmail("guilherme@gmail.com");
		
		User ryan = new User(null);
		ryan.setName("Ryan gademe");
		ryan.setGender(User.Gender.M);
		ryan.setEmail("ryan@gmail.com");
		
		User bruna = new User(null);
		bruna.setName("Ryan gademe");
		bruna.setGender(User.Gender.F);
		ryabrunan.setEmail("bruna@gmail.com");
		
		userRepository.save(guilherme);
		userRepository.save(ryan);
		userRepository.save(bruna);
		
	}
	

}
