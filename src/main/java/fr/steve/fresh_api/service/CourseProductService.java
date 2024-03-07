package fr.steve.fresh_api.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import fr.steve.fresh_api.dto.UpdateCourseProductDto;
import fr.steve.fresh_api.exception.CourseProductNotFoundException;
import fr.steve.fresh_api.model.entity.CourseProduct;
import fr.steve.fresh_api.model.repository.CourseProductRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CourseProductService {

    private final CourseProductRepository repository;

    public CourseProduct save(CourseProduct courseProduct) {
        return this.repository.save(courseProduct);
    }

    public CourseProduct get(Integer id) {
        return this.repository.findById(id).orElseThrow(() -> new CourseProductNotFoundException(id));
    }

    public CourseProduct update(Integer id, UpdateCourseProductDto dto) {
        CourseProduct target = this.repository.findById(id).orElseThrow(() -> new CourseProductNotFoundException(id));
        if (target.isTaken()) {
            return target;
        }
        if(dto.getCommentary() != null) {
            target.setCommentary(dto.getCommentary());;
        }
        return this.repository.save(target);
    }

    public void delete(Integer id) {
        CourseProduct courseProduct = this.repository.findById(id)
                .orElseThrow(() -> new CourseProductNotFoundException(id));
        if (courseProduct.isTaken()) {
            return;
        }
        this.repository.delete(courseProduct);
    }

    public CourseProduct take(Integer id) {
        CourseProduct courseProduct = this.repository.findById(id)
                .orElseThrow(() -> new CourseProductNotFoundException(id));
        if (courseProduct.isTaken()) {
            return courseProduct;
        }
        courseProduct.setTakenAt(LocalDateTime.now());
        return this.repository.save(courseProduct);
    }
}