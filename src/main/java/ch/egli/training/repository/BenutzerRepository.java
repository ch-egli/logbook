package ch.egli.training.repository;

import ch.egli.training.model.Benutzer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository for entity Benutzer.
 *
 * @author Christian Egli
 * @since 2/1/16.
 */
public interface BenutzerRepository extends CrudRepository<Benutzer, Long> {

    public Benutzer findByBenutzername(String benutzername);

    @Modifying
    @Transactional
    public void deleteByBenutzername(String benutzername);
}
