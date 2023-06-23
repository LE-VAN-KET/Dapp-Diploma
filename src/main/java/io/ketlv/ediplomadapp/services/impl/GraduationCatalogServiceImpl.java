package io.ketlv.ediplomadapp.services.impl;

import io.ketlv.ediplomadapp.domain.GraduationCatalog;
import io.ketlv.ediplomadapp.mapper.GraduationCatalogMapper;
import io.ketlv.ediplomadapp.services.GraduationCatalogService;
import io.ketlv.ediplomadapp.services.dto.CatalogPaging;
import io.ketlv.ediplomadapp.services.dto.CatalogsRes;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
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
    public CatalogsRes getAll(CatalogPaging catalogPaging) {
        Long count = graduationCatalogMapper.count();
        if (catalogPaging == null) {
            return CatalogsRes.builder().data(graduationCatalogMapper.findAll()).total(count).build();
        }
        if (catalogPaging.getPage() != null) {
            if (catalogPaging.getPage() > 0) {
                catalogPaging.setPage(catalogPaging.getPageSize() * (catalogPaging.getPage() - 1));
            } else {
                catalogPaging.setPage(0L);
            }

        }
        return CatalogsRes.builder().data(graduationCatalogMapper.findPaging(catalogPaging)).total(count).build();
    }

    @Override
    public void updateCatalog(GraduationCatalog catalog) {
        boolean isExist = graduationCatalogMapper.isExistCatalogById(catalog.getId());
        if (isExist) {
            catalog.setLastModifiedDate(Instant.now());
            graduationCatalogMapper.updateCatalog(catalog);
        } else {
            throw new RuntimeException("Not found catalog");
        }

    }

    @Override
    public void delete(Long id) {
        boolean isExist = graduationCatalogMapper.isExistCatalogById(id);
        if (isExist) {
            graduationCatalogMapper.delete(id);
        } else {
            throw new RuntimeException("Not found catalog");
        }
    }
}
