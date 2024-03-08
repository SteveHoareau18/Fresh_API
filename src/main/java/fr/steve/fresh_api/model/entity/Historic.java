package fr.steve.fresh_api.model.entity;

import java.time.LocalDateTime;


import fr.steve.fresh_api.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Historic {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private LocalDateTime status_date;

    @ManyToOne
    private CourseProduct courseProduct;

    @ManyToOne
    private User user;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Status status = Status.CREATED;
  

    public Integer getId() {
        return this.id;
    }

    public LocalDateTime getStatusDate() {
        return this.status_date;
    }

    public void setStatusDate(LocalDateTime status_date) {
        this.status_date = status_date;
    }

    
}

