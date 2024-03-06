package fr.steve.fresh_api.model.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import fr.steve.fresh_api.model.entity.Course;
import fr.steve.fresh_api.model.entity.CourseProduct;

public interface CourseProductRepository extends CrudRepository<CourseProduct, Integer> {

    public List<CourseProduct> findAllByCourse(Course course);
}
