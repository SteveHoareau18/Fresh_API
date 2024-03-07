package fr.steve.fresh_api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.steve.fresh_api.model.dto.product.CreateProductDto;
import fr.steve.fresh_api.model.dto.product.UpdateProductDto;
import fr.steve.fresh_api.model.entity.Product;

import fr.steve.fresh_api.service.ProductService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> list() {
        List<Product> products = this.productService.getAll();
        return ResponseEntity.ok(products);
    }

    @PostMapping("/product/create")
    public ResponseEntity<Product> create(@Valid @RequestBody @NonNull CreateProductDto dto) {
        Product product = this.productService.create(dto);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/product/get/{id}")
    public ResponseEntity<Product> get(@PathVariable(value = "id") @NonNull Integer id) {
        Product product = this.productService.get(id);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/product/update/{id}")
    @Transactional
    public ResponseEntity<Product> update(
            @Valid @RequestBody @NonNull UpdateProductDto dto,
            @PathVariable(value = "id") @NonNull Integer id) {
        Product product = this.productService.update(id, dto);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/product/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") @NonNull Integer id) {
        this.productService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
