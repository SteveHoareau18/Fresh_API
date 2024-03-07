package fr.steve.fresh_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class CourseProductNotFoundException extends RuntimeException {
    
    public CourseProductNotFoundException(Integer id) {
        super("CourseProduct with id " + id + " is not found");
    }
}