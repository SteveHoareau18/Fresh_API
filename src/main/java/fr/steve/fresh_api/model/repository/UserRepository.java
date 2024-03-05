package fr.steve.fresh_api.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import fr.steve.fresh_api.model.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findByName(String firstName);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}