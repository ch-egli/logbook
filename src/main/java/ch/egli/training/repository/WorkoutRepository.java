package ch.egli.training.repository;

import ch.egli.training.model.Workout;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by christian on 1/24/16.
 */
public interface WorkoutRepository extends CrudRepository<Workout, Long> {

    public List<Workout> findByBenutzer(String benutzer);
}
