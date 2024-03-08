package fr.steve.fresh_api.event.listener;

import java.time.LocalDateTime;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import fr.steve.fresh_api.event.CourseProductEvent;
import fr.steve.fresh_api.model.entity.Historic;
import fr.steve.fresh_api.service.HistoricService;

@Component
public class HistoricListener implements ApplicationListener<CourseProductEvent> {

    private HistoricService historicService;
    
    @Override
    public void onApplicationEvent(CourseProductEvent event) {
        Historic historic = Historic.builder()
            .status(event.getStatus())
            .status_date(LocalDateTime.now())
            .user(event.getOperator())
            .build();
        this.historicService.save(historic);
    }
}