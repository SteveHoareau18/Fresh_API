package fr.steve.fresh_api.model.entity;


import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;


@Entity
public class CourseProduct {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Course course;
    @ManyToOne
    private Product product;
    private String commentary;
    private LocalDateTime taken_date;

    public Long getId(){
        return this.id;
    }


    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public String getCommentary() {
        return this.commentary;
    }

    public void setTakenDate(LocalDateTime taken_date) {
        this.taken_date = taken_date;
    }

    public LocalDateTime getTakenDate() {
        return this.taken_date;
    }

    @Hidden
    public boolean isTaken(){
        return this.taken_date != null;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
