package fr.steve.fresh_api.event.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import fr.steve.fresh_api.enums.Status;
import fr.steve.fresh_api.event.CourseProductEvent;
import fr.steve.fresh_api.model.entity.CourseProduct;
import fr.steve.fresh_api.model.entity.User;

@Component
public class CourseProductEventPublisher {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    private CourseProduct courseProduct;

    public CourseProductEventPublisher(CourseProduct courseProduct){
        this.courseProduct = courseProduct;
    }

    public void publishEvent(final Status status, User operator) {
        CourseProductEvent customSpringEvent = new CourseProductEvent(courseProduct, status, operator);
        applicationEventPublisher.publishEvent(customSpringEvent);
    }
}