package io.ketlv.ediplomadapp.services;

import io.ketlv.ediplomadapp.domain.Diploma;
import io.ketlv.ediplomadapp.services.dto.*;
import org.springframework.web.multipart.MultipartFile;

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

    void partialUpdate(UpdateDiplomaReq req, MultipartFile file);

    /**
     * Get all the diplomas.
     *
     * @return the list of entities.
     */
    DiplomasRes findAll(Long page, Long size, Long yearGraduation);

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

    void verifiedDiplomas(VerifiedDiplomaReq req);
    void updateStatus(DiplomaStatusReq req, String refNumber);
    byte[] loadFileDiploma(String hash);
    List<Long> getListYearGraduation();
}

