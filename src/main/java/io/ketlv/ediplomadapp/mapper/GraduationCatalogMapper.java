package io.ketlv.ediplomadapp.mapper;

import io.ketlv.ediplomadapp.domain.GraduationCatalog;
import io.ketlv.ediplomadapp.services.dto.CatalogPaging;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GraduationCatalogMapper {
    @Select("SELECT * FROM GRADUATION_CATALOG")
    @ResultMap("catalogResultMap")
    List<GraduationCatalog> findAll();

    List<GraduationCatalog> findPaging(CatalogPaging catalogPaging);

    Long createGraduationCatalog(GraduationCatalog catalog);

    @Select("SELECT * FROM GRADUATION_CATALOG WHERE id = #{id}")
    @ResultMap("catalogResultMap")
    GraduationCatalog findById(@Param("id") Long id);

    @Select("SELECT 1 FROM GRADUATION_CATALOG WHERE id = #{id}")
    boolean isExistCatalogById(@Param("id") Long id);

    void updateCatalog(GraduationCatalog catalog);
    @Select("SELECT count(1) FROM GRADUATION_CATALOG")
    long count();

    void delete(@Param("id") Long id);
}
