package io.ketlv.ediplomadapp.services.impl;

import io.ketlv.ediplomadapp.domain.GraduationCatalog;
import io.ketlv.ediplomadapp.mapper.GraduationCatalogMapper;
import io.ketlv.ediplomadapp.services.GraduationCatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GraduationCatalogServiceImpl implements GraduationCatalogService {
    private final GraduationCatalogMapper graduationCatalogMapper;

    @Override
    public Long createGraduationCatalog(GraduationCatalog catalog) {

        return graduationCatalogMapper.createGraduationCatalog(catalog);
    }

    @Override
    public GraduationCatalog getOneByID(Long catalogId) {
        boolean isExist = graduationCatalogMapper.isExistCatalogById(catalogId);
        if (isExist) {
            return graduationCatalogMapper.findById(catalogId);
        }
        throw new RuntimeException();
    }

    @Override
    public List<GraduationCatalog> getAll() {
        return graduationCatalogMapper.findAll();
    }
}
