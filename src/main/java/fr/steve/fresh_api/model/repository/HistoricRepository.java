package fr.steve.fresh_api.model.repository;

import org.springframework.data.repository.CrudRepository;

import fr.steve.fresh_api.model.entity.Historic;

public interface HistoricRepository extends CrudRepository<Historic, Integer> {

}
