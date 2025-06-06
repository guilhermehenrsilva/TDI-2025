package br.edu.ifsuldeminas.mch.springbootcrud.model.Repository;


import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	
}
    // Custom query methods can be defined here if needed
    // For example:
    // List<User> findByName(String name);