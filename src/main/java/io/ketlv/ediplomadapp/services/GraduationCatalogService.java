package io.ketlv.ediplomadapp.services;

import io.ketlv.ediplomadapp.domain.GraduationCatalog;
import io.ketlv.ediplomadapp.services.dto.CatalogPaging;
import io.ketlv.ediplomadapp.services.dto.CatalogsRes;

import java.util.List;

public interface GraduationCatalogService {
    Long createGraduationCatalog(GraduationCatalog catalog);
    GraduationCatalog getOneByID(Long catalogId);
    CatalogsRes getAll(CatalogPaging catalogPaging);
    void updateCatalog(GraduationCatalog catalog);
    void delete(Long id);
}
