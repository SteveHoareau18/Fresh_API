package fr.steve.fresh_api.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.steve.fresh_api.enums.Role;
import fr.steve.fresh_api.model.dto.course.CreateCourseDto;
import fr.steve.fresh_api.model.dto.course.UpdateCourseDto;
import fr.steve.fresh_api.model.dto.course_product.CreateCourseProductDto;
import fr.steve.fresh_api.model.dto.course_product.UpdateCourseProductDto;
import fr.steve.fresh_api.model.entity.Course;
import fr.steve.fresh_api.model.entity.CourseProduct;
import fr.steve.fresh_api.model.entity.Product;
import fr.steve.fresh_api.model.entity.User;
import fr.steve.fresh_api.service.CourseProductService;
import fr.steve.fresh_api.service.CourseService;
import fr.steve.fresh_api.service.ProductService;
import fr.steve.fresh_api.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final ProductService productService;
    private final CourseProductService courseProductService;
    private final UserService userService;

    @GetMapping("/courses")
    public ResponseEntity<List<Course>> list(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<Course> courses = this.courseService.getAll(user);
        return ResponseEntity.ok(courses);
    }

    @PostMapping("/course/create")
    public ResponseEntity<Course> create(@Valid @RequestBody CreateCourseDto dto) {
        Course course = this.courseService.create(dto);
        return ResponseEntity.ok(course);
    }

    @GetMapping("/course/get/{id}")
    public ResponseEntity<Course> get(
            @PathVariable(value = "id") @NonNull Integer id,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Course course = this.courseService.get(id);
        if (!course.canAccess(user)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(course);
    }

    @PutMapping("/course/update/{id}")
    @Transactional
    public ResponseEntity<Course> update(
            @RequestBody @NonNull UpdateCourseDto dto,
            @PathVariable(value = "id") @NonNull Integer id,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Course course = this.courseService.get(id);
        if (!course.canAccess(user)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        course = this.courseService.update(course, dto);
        return ResponseEntity.ok(course);
    }

    @PutMapping("/course/finish/{id}")
    public ResponseEntity<Course> finish(
            @PathVariable(value = "id") @NonNull Integer id,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Course course = this.courseService.get(id);
        if (course.getOwner().getId() != user.getId()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        course = this.courseService.finish(course);
        return ResponseEntity.ok(course);
    }

    @PutMapping("/course/share/{id}/to/{userId}")
    public ResponseEntity<Void> share(
            @PathVariable(value = "id") @NonNull Integer id,
            @PathVariable(value = "userId") @NonNull Long userId,
            Authentication authentication) {
        User authUser = (User) authentication.getPrincipal();
        Course course = this.courseService.get(id);
        User user = this.userService.get(userId);

        if (!course.getOwner().equals(authUser) || course.getOwner().equals(user)) {
            return ResponseEntity.badRequest().build();
        }

        this.courseService.share(course, user);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/course/unshare/{id}/to/{userId}")
    public ResponseEntity<Void> unshare(
            @PathVariable(value = "id") @NonNull Integer id,
            @PathVariable(value = "userId") @NonNull Long userId,
            Authentication authentication) {
        User authUser = (User) authentication.getPrincipal();
        Course course = this.courseService.get(id);
        User user = this.userService.get(userId);

        if (!course.getOwner().equals(authUser)) {
            return ResponseEntity.badRequest().build();
        }

        this.courseService.unshare(course, user);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/course/delete/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable(value = "id") @NonNull Integer id,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Course course = this.courseService.get(id);
        if (!course.getOwner().equals(user)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        this.courseService.delete(course);
        return ResponseEntity.noContent().build();
    }

    @PostMapping({ "/course/{id}/product", "/course/{id}/product/{productId}" })
    public ResponseEntity<CourseProduct> addProduct(
            @PathVariable(name = "id") Integer id,
            @PathVariable(name = "productId", required = false) Integer productId,
            @RequestBody CreateCourseProductDto dto,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Course course = this.courseService.get(id);
        if (!course.canAccess(user)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Product product;
        try {
            product = this.productService.get(productId);
        } catch (Exception e) {
            product = Product.builder()
                    .name(dto.getProduct().getName())
                    .build();
            this.productService.save(product);
        }

        CourseProduct courseProduct = CourseProduct.builder()
                .course(course)
                .product(product)
                .commentary(dto.getCommentary())
                .takenAt(dto.isTaken() ? LocalDateTime.now() : null)
                .build();

        return ResponseEntity.ok(this.courseProductService.save(courseProduct));
    }

    @PutMapping("/course-product/{id}/take")
    public ResponseEntity<CourseProduct> takeProduct(
            @PathVariable("id") @NonNull Integer id,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        CourseProduct courseProduct = this.courseProductService.get(id);
        if (!courseProduct.getCourse().canAccess(user)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        this.courseProductService.take(courseProduct);
        return ResponseEntity.ok(courseProduct);
    }

    @PutMapping("/course-product/{id}")
    public ResponseEntity<CourseProduct> updateProduct(
            @PathVariable("id") @NonNull Integer id,
            @RequestBody @NonNull UpdateCourseProductDto dto, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        
        CourseProduct courseProduct = this.courseProductService.get(id);
        if (!courseProduct.getCourse().canAccess(user)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Product product;
        try {
            product = this.productService.get(dto.getProductId());
        } catch (Exception e) {
            product = this.productService.create(dto.getProduct(), user);
        }
        courseProduct.setProduct(product);
        this.courseProductService.update(courseProduct, dto);
        return ResponseEntity.ok(courseProduct);
    }

    @DeleteMapping("/course-product/{id}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable("id") @NonNull Integer id,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        CourseProduct courseProduct = this.courseProductService.get(id);
        if (!courseProduct.getCourse().canAccess(user)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        this.courseProductService.delete(courseProduct);
        return ResponseEntity.noContent().build();
    }

}
