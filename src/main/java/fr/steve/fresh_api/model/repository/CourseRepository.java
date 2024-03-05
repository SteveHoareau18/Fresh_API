package fr.steve.fresh_api.model.repository;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import fr.steve.fresh_api.model.entity.Course;

public interface CourseRepository extends CrudRepository<Course, Integer> {

    List<Course> findByTitle(String title);}
