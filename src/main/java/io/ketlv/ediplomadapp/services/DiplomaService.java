package io.ketlv.ediplomadapp.services;

import io.ketlv.ediplomadapp.domain.Diploma;
import io.ketlv.ediplomadapp.services.dto.DiplomaDto;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Diploma}.
 */
public interface DiplomaService {
    /**
     * Save a diploma.
     *
     * @param diploma the entity to save.
     * @return the persisted entity.
     */
    Diploma save(Diploma diploma);

    /**
     * Updates a diploma.
     *
     * @param diploma the entity to update.
     * @return the persisted entity.
     */
    Diploma update(Diploma diploma);

    /**
     * Partially updates a diploma.
     *
     * @param diploma the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Diploma> partialUpdate(Diploma diploma);

    /**
     * Get all the diplomas.
     *
     * @return the list of entities.
     */
    List<DiplomaDto> findAll();

    /**
     * Get the "id" diploma.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Diploma findOne(Long id);

    /**
     * Get the "serial number" diploma.
     *
     * @param serialNumber the serial number of the entity.
     * @return the entity.
     */
    Diploma findOneBySerialNumber(String serialNumber);

    /**
     * Delete the "id" diploma.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

