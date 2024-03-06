package fr.steve.fresh_api.service;

import org.springframework.stereotype.Service;

import fr.steve.fresh_api.model.entity.Course;
import fr.steve.fresh_api.model.entity.CourseProduct;
import fr.steve.fresh_api.model.repository.CourseProductRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CourseService {

    private final CourseProductRepository repository;

    // public Iterable<CourseProductList> getProducts(Course course) {
    //     return this.repository.
    // }
}