package fr.steve.fresh_api.model.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import fr.steve.fresh_api.model.entity.Product;
import fr.steve.fresh_api.model.entity.User;

public interface ProductRepository extends CrudRepository<Product, Integer> {

    List<Product> findByName(String name);

    List<Product> findAllByOwnerOrOwnerNull(User user);
}
