package fr.steve.fresh_api.service;

import java.util.List;
import org.springframework.stereotype.Service;

import fr.steve.fresh_api.enums.Role;
import fr.steve.fresh_api.exception.ProductNotFoundException;
import fr.steve.fresh_api.model.dto.product.CreateProductDto;
import fr.steve.fresh_api.model.dto.product.UpdateProductDto;
import fr.steve.fresh_api.model.entity.Product;
import fr.steve.fresh_api.model.entity.User;
import fr.steve.fresh_api.model.repository.ProductRepository;
import fr.steve.fresh_api.util.ListUtils;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public List<Product> getAll() {
        return ListUtils.iteratorToList(this.repository.findAll());
    }

    public List<Product> getAll(User user) {
        return ListUtils.iteratorToList(this.repository.findAllByOwnerOrOwnerNull(user));
    }

    public Product save(Product product) {
        return this.repository.save(product);
    }

    public Product get(Integer id) {
        return this.repository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product create(CreateProductDto dto, User user) {
        Product product = Product.builder()
                .name(dto.getName())
                .owner(user == null || user.getRole()==Role.ADMIN?null:user)
                .build();        
        return this.repository.save(product);
    }

    public Product update(Integer id, UpdateProductDto dto) {
        Product target = this.repository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        if(dto.getName() != null) {
            target.setName(dto.getName());
        }
        return this.repository.save(target);
    }

    public void delete(Integer id) {
        Product product = this.repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        this.repository.delete(product);
    }
}