package ch.egli.training.repository;

import ch.egli.training.model.Benutzer;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by christian on 2/1/16.
 */
public interface BenutzerRepository extends CrudRepository<Benutzer, Long> {
}
