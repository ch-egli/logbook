package ch.egli.training.repository;

import ch.egli.training.model.Workout;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by christian on 1/24/16.
 */
public interface WorkoutRepository extends CrudRepository<Workout, Long> {
}
