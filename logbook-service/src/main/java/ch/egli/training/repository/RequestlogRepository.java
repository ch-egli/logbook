package ch.egli.training.repository;

import ch.egli.training.model.Requestlog;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Christian Egli
 * @since 12/30/16.
 */
public interface RequestlogRepository extends CrudRepository<Requestlog, Long> {
}
