package fr.steve.fresh_api.model.repository;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import fr.steve.fresh_api.model.entity.Course;
import fr.steve.fresh_api.model.entity.User;

public interface CourseRepository extends CrudRepository<Course, Integer> {

    List<Course> findByTitle(String title);

    @Query("SELECT c FROM Course c WHERE :owner MEMBER OF c.participants OR c.owner = :owner")
    List<Course> findAllByOwnerOrInParticipants(User owner);
}
