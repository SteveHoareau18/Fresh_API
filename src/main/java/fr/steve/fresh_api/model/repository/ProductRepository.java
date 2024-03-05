package fr.steve.fresh_api.model.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import fr.steve.fresh_api.model.entity.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {

    List<Product> findByName(String name);}
