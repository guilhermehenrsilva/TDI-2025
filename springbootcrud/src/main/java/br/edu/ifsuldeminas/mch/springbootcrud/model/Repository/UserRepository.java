package br.edu.ifsuldeminas.mch.springbootcrud.model.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import br.edu.ifsuldeminas.mch.springbootcrud.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // Custom query methods can be defined here if needed
    // For example:
    // List<User> findByName(String name);

}
