package ch.egli.training.repository;

import ch.egli.training.model.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

/**
 * Repository for entity Status.
 *
 * @author Christian Egli
 * @since 20.06.2016
 */
@Transactional
public interface StatusRepository extends JpaRepository<Status, Long> {

    public int countByBenutzername(String benutzername);

    public Page<Status> findByBenutzername(String benutzername, Pageable pageable);

    @Query("select count(s) from Status s where s.benutzername=?1")
    public int countAllStatusEntriesOfGivenUsers(String name);

    @Query("select s from Status s where s.benutzername=?1")
    public Page<Status> findAllStatusEntriesOfGivenUser(String name, Pageable pageable);

    @Query("select s from Status s where s.benutzername=?1 ORDER BY s.datum DESC")
    public Page<Status> findTopByBenutzername(String benutzername, Pageable pageable);

    public Status findById(Long statusId);

    @Query("select s from Status s where YEAR(s.datum)=?1 and s.benutzername=?2")
    public List<Status> findByYearAndBenutzer(Integer year, String benutzername);

    public Status findByBenutzernameAndId(String benutzername, Long statusId);

    public Status findByBenutzernameAndDatum(String benutzername, Date date);
}
