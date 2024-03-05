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

import fr.steve.fresh_api.exception.CourseNotFoundException;
import fr.steve.fresh_api.exception.ProductNotFoundException;
import fr.steve.fresh_api.model.entity.Course;

import fr.steve.fresh_api.model.repository.CourseRepository;
import jakarta.transaction.Transactional;

@RestController
public class CourseController {
    
     private CourseRepository courseRepository;

    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @GetMapping("/courses")
    public List<Course> list() {
        return StreamSupport.stream(courseRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
   
    @PostMapping("/course/create")
    public Course create(@RequestBody @NonNull Course course) {
        courseRepository.save(course);
        return course;
    }

    @GetMapping("/course/get/{id}")
    public Course get(@PathVariable(value = "id") @NonNull Integer id) {
        return courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException(id));
    }

    @PutMapping("/course/update/{id}")
    @Transactional
    public Course update(@RequestBody @NonNull Course course, @PathVariable(value = "id") @NonNull Integer id) {
        Course courseDb = courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException(id));
        
        
        courseDb.setTitle(course.getTitle());
        if (courseDb != null)
            this.courseRepository.save(courseDb);
        return courseDb;
    }

    @DeleteMapping("/course/delete/{id}")
    public String delete(@PathVariable(value = "id") @NonNull Integer id) {
        Course coursetDb = courseRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        courseRepository.delete(coursetDb);
        return "Le produit ayant l'id: " + id + ", a bien été supprimé.";
    }

}
