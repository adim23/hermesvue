package gr.adr.hermes.service;

import gr.adr.hermes.domain.ContactTypes;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ContactTypes}.
 */
public interface ContactTypesService {
    /**
     * Save a contactTypes.
     *
     * @param contactTypes the entity to save.
     * @return the persisted entity.
     */
    ContactTypes save(ContactTypes contactTypes);

    /**
     * Partially updates a contactTypes.
     *
     * @param contactTypes the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ContactTypes> partialUpdate(ContactTypes contactTypes);

    /**
     * Get all the contactTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ContactTypes> findAll(Pageable pageable);

    /**
     * Get the "id" contactTypes.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ContactTypes> findOne(Long id);

    /**
     * Delete the "id" contactTypes.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the contactTypes corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ContactTypes> search(String query, Pageable pageable);
}
