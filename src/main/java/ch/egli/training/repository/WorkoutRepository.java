package ch.egli.training.repository;

import ch.egli.training.model.Workout;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repository for entity Benutzer.
 *
 * @author Christian Egli
 * @since 24/1/16.
 */
public interface WorkoutRepository extends CrudRepository<Workout, Long> {

    public List<Workout> findByBenutzername(String benutzername);

    public Workout findByBenutzernameAndId(String benutzername, Long workoutId);

    @Query("select w from Workout w where YEAR(w.datum)=?1 and w.benutzername=?2")
    public List<Workout> findByYearAndBenutzer(Integer year, String benutzername);
}
