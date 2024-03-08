package fr.steve.fresh_api.service;

import org.springframework.stereotype.Service;

import fr.steve.fresh_api.model.entity.Historic;
import fr.steve.fresh_api.model.repository.HistoricRepository;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class HistoricService {
    
    private HistoricRepository historicRepository;

    public Historic save(Historic historic){
        this.historicRepository.save(historic);
        return historic;
    }
}
