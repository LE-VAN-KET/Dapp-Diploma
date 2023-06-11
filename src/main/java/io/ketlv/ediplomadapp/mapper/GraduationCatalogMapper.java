package io.ketlv.ediplomadapp.mapper;

import io.ketlv.ediplomadapp.domain.GraduationCatalog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GraduationCatalogMapper {
    @Select("SELECT * FROM GRADUATION_CATALOG")
    @ResultMap("catalogResultMap")
    List<GraduationCatalog> findAll();

    Long createGraduationCatalog(GraduationCatalog catalog);

    @Select("SELECT * FROM GRADUATION_CATALOG WHERE id = #{id}")
    GraduationCatalog findById(@Param("id") Long id);

    @Select("SELECT 1 FROM GRADUATION_CATALOG WHERE id = #{id}")
    boolean isExistCatalogById(@Param("id") Long id);
}
