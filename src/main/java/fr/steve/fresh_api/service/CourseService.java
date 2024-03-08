package fr.steve.fresh_api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import fr.steve.fresh_api.exception.CourseNotFoundException;
import fr.steve.fresh_api.model.dto.course.CreateCourseDto;
import fr.steve.fresh_api.model.dto.course.UpdateCourseDto;
import fr.steve.fresh_api.model.entity.Course;
import fr.steve.fresh_api.model.entity.User;
import fr.steve.fresh_api.model.repository.CourseRepository;
import fr.steve.fresh_api.util.ListUtils;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CourseService {

    private final CourseRepository repository;

    public List<Course> getAll() {
        return ListUtils.iteratorToList(this.repository.findAll());
    }

    public List<Course> getAll(User user) {
        return this.repository.findAllByOwnerOrInParticipants(user);
    }

    public Course create(CreateCourseDto dto) {
        Course course = Course.builder()
                .title(dto.getTitle())
                .token(UUID.randomUUID().toString())
                .build();
        return this.repository.save(course);
    }

    public Course get(Integer id) {
        return this.repository.findById(id).orElseThrow(() -> new CourseNotFoundException(id));
    }

    public Course update(Integer id, UpdateCourseDto dto) {
        Course target = this.repository.findById(id).orElseThrow(() -> new CourseNotFoundException(id));
        if (dto.getTitle() != null) {
            target.setTitle(dto.getTitle());
        }
        return this.repository.save(target);
    }

    public Course update(Course target, UpdateCourseDto dto) {
        if (target.isFinished()) {
            return target;
        }
        if (dto.getTitle() != null) {
            target.setTitle(dto.getTitle());
        }
        return this.repository.save(target);
    }

    public void delete(Integer id) {
        Course course = this.repository.findById(id).orElseThrow(() -> new CourseNotFoundException(id));
        this.repository.delete(course);
    }

    public void delete(Course course) {
        this.repository.delete(course);
    }

    public Course finish(Integer id) {
        Course course = this.repository.findById(id).orElseThrow(() -> new CourseNotFoundException(id));
        if (course.isFinished()) {
            return course;
        }
        course.setFinishedAt(LocalDateTime.now());
        return this.repository.save(course);
    }

    public Course finish(Course course) {
        if (course.isFinished()) {
            return course;
        }
        course.setFinishedAt(LocalDateTime.now());
        return this.repository.save(course);
    }

    public void share(Course course, User user) {
        course.addParticipant(user);
        this.repository.save(course);
    }

    public void unshare(Course course, User user) {
        course.removeParticipant(user);
        this.repository.save(course);
    }
}