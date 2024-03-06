package fr.steve.fresh_api.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.steve.fresh_api.exception.CourseNotFoundException;
import fr.steve.fresh_api.exception.CourseProductNotFoundException;
import fr.steve.fresh_api.exception.ProductNotFoundException;
import fr.steve.fresh_api.model.entity.Course;
import fr.steve.fresh_api.model.entity.CourseProduct;
import fr.steve.fresh_api.model.entity.Product;
import fr.steve.fresh_api.model.repository.CourseProductRepository;
import fr.steve.fresh_api.model.repository.CourseRepository;
import fr.steve.fresh_api.model.repository.ProductRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;

@RestController
public class CourseController {
    
     private CourseRepository courseRepository;
     private ProductRepository productRepository;
     private CourseProductRepository courseProductRepository;

    public CourseController(CourseRepository courseRepository, ProductRepository productRepository, CourseProductRepository courseProductRepository) {
        this.courseRepository = courseRepository;
        this.productRepository = productRepository;
        this.courseProductRepository = courseProductRepository;
    }

    @GetMapping("/courses")
    public List<Course> list() {
        return StreamSupport.stream(courseRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
   
    @PostMapping("/course/create")
    public Course create(@RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(example = "title"))) @NonNull Course course) {
        courseRepository.save(course);
        return course;
    }

    @PostMapping("/course/{id}/product/{productId}")
    public CourseProduct addProduct(@PathVariable("id") @NonNull Integer id, @PathVariable("productId") @NonNull Integer productId, @RequestBody @NonNull CourseProduct courseProduct) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException(id));
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));
        courseProduct.setCourse(course);
        courseProduct.setProduct(product);
        courseProductRepository.save(courseProduct);
        return courseProduct;
    }

    @PutMapping("/course-product/{id}/take")
    public ResponseEntity<CourseProduct> takeProduct(@PathVariable("id") @NonNull Integer id) {
        CourseProduct courseProductDb = courseProductRepository.findById(id).orElseThrow(() -> new CourseProductNotFoundException(id));
        if(courseProductDb.isTaken()) return ResponseEntity.badRequest().build();
        courseProductDb.setTakenDate(LocalDateTime.now());
        courseProductRepository.save(courseProductDb);
        return ResponseEntity.ok(courseProductDb);
    }

    @PutMapping("/course-product/{id}")
    public ResponseEntity<CourseProduct> updateProduct(@PathVariable("id") @NonNull Integer id, @RequestBody @NonNull CourseProduct courseProduct) {
        CourseProduct courseProductDb = courseProductRepository.findById(id).orElseThrow(() -> new CourseProductNotFoundException(id));
        if(courseProductDb.isTaken()) return ResponseEntity.badRequest().build();
        courseProductDb.setCommentary(courseProduct.getCommentary());
        courseProductRepository.save(courseProductDb);
        return ResponseEntity.ok(courseProductDb);
    }

    @DeleteMapping("/course-product/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") @NonNull Integer id) {
        CourseProduct courseProductDb = courseProductRepository.findById(id).orElseThrow(() -> new CourseProductNotFoundException(id));
        if(courseProductDb.isTaken()) return ResponseEntity.badRequest().build();
        courseProductRepository.delete(courseProductDb);
        return ResponseEntity.noContent().build();
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
    public ResponseEntity<Void> delete(@PathVariable(value = "id") @NonNull Integer id) {
        Course coursetDb = courseRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        courseRepository.delete(coursetDb);
        return ResponseEntity.noContent().build();
    }

}
