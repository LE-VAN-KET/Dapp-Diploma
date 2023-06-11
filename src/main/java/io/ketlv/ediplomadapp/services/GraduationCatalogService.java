package io.ketlv.ediplomadapp.services;

import io.ketlv.ediplomadapp.domain.GraduationCatalog;

import java.util.List;

public interface GraduationCatalogService {
    Long createGraduationCatalog(GraduationCatalog catalog);
    GraduationCatalog getOneByID(Long catalogId);
    List<GraduationCatalog> getAll();
}
