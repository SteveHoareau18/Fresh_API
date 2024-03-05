package fr.steve.fresh_api.model.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import fr.steve.fresh_api.model.entity.User;

public interface UserRepository extends CrudRepository<User, Integer> {

    List<User> findByName(String firstName);
}