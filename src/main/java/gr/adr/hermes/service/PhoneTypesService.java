package gr.adr.hermes.service;

import gr.adr.hermes.domain.PhoneTypes;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link PhoneTypes}.
 */
public interface PhoneTypesService {
    /**
     * Save a phoneTypes.
     *
     * @param phoneTypes the entity to save.
     * @return the persisted entity.
     */
    PhoneTypes save(PhoneTypes phoneTypes);

    /**
     * Partially updates a phoneTypes.
     *
     * @param phoneTypes the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PhoneTypes> partialUpdate(PhoneTypes phoneTypes);

    /**
     * Get all the phoneTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PhoneTypes> findAll(Pageable pageable);

    /**
     * Get the "id" phoneTypes.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PhoneTypes> findOne(Long id);

    /**
     * Delete the "id" phoneTypes.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the phoneTypes corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PhoneTypes> search(String query, Pageable pageable);
}
