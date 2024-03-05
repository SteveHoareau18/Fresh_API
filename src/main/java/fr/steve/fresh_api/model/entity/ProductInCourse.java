package fr.steve.fresh_api.model.entity;


import java.time.LocalDateTime;

import jakarta.persistence.Entity;



@Entity
public class ProductInCourse {
    
    private Course course;
    private Product product;
    private String commentary;
    private LocalDateTime is_taken;


    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public String getCommentary() {
        return this.commentary;
    }

    public void setIs_taken(LocalDateTime is_taken) {
        this.is_taken = is_taken;
    }

    public LocalDateTime getIs_taken() {
        return this.is_taken;
    }

    
}
