package fr.steve.fresh_api.model.entity;


import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String title, token;
    private LocalDateTime crated_at, finished_at;
    
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

    public LocalDateTime getCrated_at() {
        return this.crated_at;
    }

    public void setCrated_at(LocalDateTime crated_at) {
        this.crated_at = crated_at;
    }

    public LocalDateTime getFinished_at() {
        return this.finished_at;
    }

    public void setFinished_at(LocalDateTime finished_at) {
        this.finished_at = finished_at;
    }
}
