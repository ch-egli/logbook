package ch.egli.training.repository;

import ch.egli.training.model.Workout;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Repository for entity Benutzer.
 *
 * @author Christian Egli
 * @since 24/1/16.
 */
@Transactional
public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    public int countByBenutzername(String benutzername);

    public Page<Workout> findByBenutzername(String benutzername, Pageable pageable);

    @Query("select count(w) from Workout w where w.benutzername=?1 or w.benutzername=?2")
    public int countAllWorkoutsOfGivenUsers(String name1, String name2);

    @Query("select w from Workout w where (w.benutzername=?1 or w.benutzername=?2)")
    public Page<Workout> findAllWorkoutsOfGivenUsers(String name1, String name2, Pageable pageable);


    @Query("select w from Workout w where w.benutzername=?1 ORDER BY w.datum DESC")
    public Page<Workout> findTopByBenutzername(String benutzername, Pageable pageable);

    public Workout findById(Long workoutId);

    public Workout findByBenutzernameAndId(String benutzername, Long workoutId);

    @Query("select w from Workout w where YEAR(w.datum)=?1 and w.benutzername=?2 ORDER BY w.datum ASC")
    public List<Workout> findByYearAndBenutzer(Integer year, String benutzername);

    @Modifying
    @Query("delete from Workout w where YEAR(w.datum)=?1 and w.benutzername=?2")
    public void deleteByYearAndBenutzer(Integer year, String benutzername);

    public List<Workout> findTop8ByOrderByDatumDesc();
}
