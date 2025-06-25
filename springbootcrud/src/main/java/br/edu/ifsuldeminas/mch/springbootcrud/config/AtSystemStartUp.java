package br.edu.ifsuldeminas.mch.springbootcrud.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.edu.ifsuldeminas.mch.springbootcrud.model.entity.User;
import br.edu.ifsuldeminas.mch.springbootcrud.model.repository.UserRepository;
import jakarta.transaction.Transactional;

@Component
@Transactional
public class AtSystemStartUp implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;
    List<User> users = new ArrayList<>();
    @Override
    public void run(String... args) throws Exception{
        User lucas = new User();
        lucas.setName("Lucas");
        lucas.setGender(User.Gender.M);
        lucas.setEmail("lucas_viana@mail.com");

        User leticia = new  User();
        leticia.setName("leticia");
        leticia.setGender(User.Gender.F);
        leticia.setEmail("leticia@mail.com");

        User fabricio = new  User();
        fabricio.setName("fabricio");
        fabricio.setGender(User.Gender.F);
        fabricio.setEmail("fabricio@mail.com");

        users.add(lucas);
        users.add(leticia);
        users.add(fabricio);

        userRepository.saveAll(users);
    }
}
