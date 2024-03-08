package fr.steve.fresh_api.event;

import org.springframework.context.ApplicationEvent;

import fr.steve.fresh_api.enums.Status;
import fr.steve.fresh_api.model.entity.CourseProduct;
import fr.steve.fresh_api.model.entity.User;
import lombok.Getter;

@Getter
public class CourseProductEvent extends ApplicationEvent {
    private Status status;
    private User operator;

    public CourseProductEvent(CourseProduct source, Status status, User operator) {
        super(source);
        this.status = status;
        this.operator = operator;
    }
}