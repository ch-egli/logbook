package ch.egli.training.repository;

import ch.egli.training.model.Benutzer;
import ch.egli.training.model.Workout;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by christian on 1/24/16.
 */
public interface WorkoutRepository extends CrudRepository<Workout, Long> {

    public List<Workout> findByBenutzername(String benutzername);

    @Query("select w from Workout w where YEAR(w.datum)=?1 and w.benutzername=?2")
    public List<Workout> findByYearAndBenutzer(Integer year, String benutzername);
}
