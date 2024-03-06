package fr.steve.fresh_api.model.entity;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String title, token;
    private LocalDateTime created_at, finished_at;
    @OneToMany(mappedBy = "course")
    private List<CourseProduct> courseProducts;
    @ManyToOne
    @CreatedBy
    private User owner;
    
    public Integer getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getCreatedAt() {
        return this.created_at;
    }

    public void setCreatedAt(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getFinishedAt() {
        return this.finished_at;
    }

    public void setFinishedAt(LocalDateTime finished_at) {
        this.finished_at = finished_at;
    }

    public List<CourseProduct> getCourseProducts() {
        return courseProducts;
    }

    public void setCourseProducts(List<CourseProduct> courseProducts) {
        this.courseProducts = courseProducts;
    }

    public void addCourseLine(CourseProduct courseProductList) {
        this.courseProducts.add(courseProductList);
    }

    public void removeCourseLine(CourseProduct courseProductList) {
        this.courseProducts.remove(courseProductList);
    }

    public User getOwner(){
        return this.owner;
    }

    public void setOwner(User user){
        this.owner = user;
    }
}
