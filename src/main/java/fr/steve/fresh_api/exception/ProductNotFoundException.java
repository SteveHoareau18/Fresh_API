package fr.steve.fresh_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends RuntimeException {
    
    public ProductNotFoundException(Integer id) {
        super("Product with id " + id + " is not found");
    }
}
