package fr.steve.fresh_api.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.steve.fresh_api.exception.ProductNotFoundException;
import fr.steve.fresh_api.model.entity.Product;

import fr.steve.fresh_api.model.repository.ProductRepository;
import jakarta.transaction.Transactional;



@RestController
public class ProductController {
    
   private ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/products")
    public List<Product> list() {
        return StreamSupport.stream(productRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
   
    @PostMapping("/product/create")
    public Product create(@RequestBody @NonNull Product product) {
        productRepository.save(product);
        return product;
    }

    @GetMapping("/product/get/{id}")
    public Product get(@PathVariable(value = "id") @NonNull Integer id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    @PutMapping("/product/update/{id}")
    @Transactional
    public Product update(@RequestBody @NonNull Product product, @PathVariable(value = "id") @NonNull Integer id) {
        Product productDb = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        
        
        productDb.setName(product.getName());
        if (productDb != null)
            this.productRepository.save(productDb);
        return productDb;
    }

    @DeleteMapping("/product/delete/{id}")
    public String delete(@PathVariable(value = "id") @NonNull Integer id) {
        Product productDb = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        productRepository.delete(productDb);
        return "Le produit ayant l'id: " + id + ", a bien été supprimé.";
    }

}
