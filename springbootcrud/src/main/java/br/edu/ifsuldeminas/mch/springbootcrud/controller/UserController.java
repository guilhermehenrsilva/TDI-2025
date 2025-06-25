package br.edu.ifsuldeminas.mch.springbootcrud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.edu.ifsuldeminas.mch.springbootcrud.model.entity.User;
import br.edu.ifsuldeminas.mch.springbootcrud.model.repository.UserRepository;


@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/users")
    public String users(Model model) {
        List<User> users = userRepository.findAll();

        model.addAttribute("usuarios", users);
        return "index.html";
    }
}
