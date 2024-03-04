package fr.steve.fresh_api.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import fr.steve.fresh_api.entity.User;

public interface UserRepository extends CrudRepository<User, Integer> {

    List<User> findByName(String firstName);
}