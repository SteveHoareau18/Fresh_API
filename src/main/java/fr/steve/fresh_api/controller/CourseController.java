package fr.steve.fresh_api.controller;

import java.time.LocalDateTime;
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
import fr.steve.fresh_api.model.dto.course.CreateCourseDto;
import fr.steve.fresh_api.model.dto.course.UpdateCourseDto;
import fr.steve.fresh_api.model.dto.course_product.CreateCourseProductDto;
import fr.steve.fresh_api.model.dto.course_product.UpdateCourseProductDto;
import fr.steve.fresh_api.model.entity.Course;
import fr.steve.fresh_api.model.entity.CourseProduct;
import fr.steve.fresh_api.model.entity.Product;
import fr.steve.fresh_api.service.CourseProductService;
import fr.steve.fresh_api.service.CourseService;
import fr.steve.fresh_api.service.ProductService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final ProductService productService;
    private final CourseProductService courseProductService;

    @GetMapping("/courses")
    public ResponseEntity<List<Course>> list() {
        List<Course> courses = this.courseService.getAll();
        return ResponseEntity.ok(courses);
    }

    @PostMapping("/course/create")
    public ResponseEntity<Course> create(@Valid @RequestBody CreateCourseDto dto) {
        Course course = this.courseService.create(dto);
        return ResponseEntity.ok(course);
    }

    @GetMapping("/course/get/{id}")
    public ResponseEntity<Course> get(@PathVariable(value = "id") @NonNull Integer id) {
        Course course = this.courseService.get(id);
        return ResponseEntity.ok(course);
    }

    @PutMapping("/course/update/{id}")
    @Transactional
    public ResponseEntity<Course> update(@RequestBody @NonNull UpdateCourseDto dto,
            @PathVariable(value = "id") @NonNull Integer id) {
        Course course = this.courseService.update(id, dto);
        return ResponseEntity.ok(course);
    }

    @PutMapping("/course/finish/{id}")
    public ResponseEntity<Course> finish(@PathVariable(value = "id") @NonNull Integer id) {
        Course course = this.courseService.finish(id);
        return ResponseEntity.ok(course);
    }

    @DeleteMapping("/course/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") @NonNull Integer id) {
        this.courseService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping({"/course/{id}/product","/course/{id}/product/{productId}"})
    public CourseProduct addProduct(
            @PathVariable(name = "id") Integer id,
            @PathVariable(name = "productId", required = false) Integer productId,
            @RequestBody CreateCourseProductDto dto) {
        Course course = this.courseService.get(id);
        Product product;
        try{
            product = this.productService.get(productId);
        }catch(Exception e){
            product = new Product();
            product.setName(dto.getProduct().getName());
            this.productService.save(product);
        }

        CourseProduct courseProduct = CourseProduct.builder()
                .course(course)
                .product(product)
                .commentary(dto.getCommentary())
                .takenAt(dto.isTaken() ? LocalDateTime.now() : null)
                .build();

        return this.courseProductService.save(courseProduct);
    }

    @PutMapping("/course-product/{id}/take")
    public ResponseEntity<CourseProduct> takeProduct(@PathVariable("id") @NonNull Integer id) {
        CourseProduct courseProduct = this.courseProductService.take(id);
        return ResponseEntity.ok(courseProduct);
    }

    @PutMapping("/course-product/{id}")
    public ResponseEntity<CourseProduct> updateProduct(
            @PathVariable("id") @NonNull Integer id,
            @RequestBody @NonNull UpdateCourseProductDto dto) {
        CourseProduct courseProduct = this.courseProductService.update(id, dto);
        return ResponseEntity.ok(courseProduct);
    }

    @DeleteMapping("/course-product/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") @NonNull Integer id) {
        this.courseProductService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
