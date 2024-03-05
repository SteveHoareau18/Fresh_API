package fr.steve.fresh_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class CourseNotFoundException extends RuntimeException { 

    public CourseNotFoundException(Integer id) {
        super("Course with id " + id + " is not found");
    }
}
