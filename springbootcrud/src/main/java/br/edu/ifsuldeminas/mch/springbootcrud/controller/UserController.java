package br.edu.ifsuldeminas.mch.springbootcrud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.edu.ifsuldeminas.mch.springbootcrud.model.Repository.UserRepository;

public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	
	@GetMapping("/users")
	public String users(Model model) {
		List<User> users = userRepository.findAll();
		
		model.addAllAttributes("usuarios", users);
		
		
		
		return"index.html";
		
	}
}
