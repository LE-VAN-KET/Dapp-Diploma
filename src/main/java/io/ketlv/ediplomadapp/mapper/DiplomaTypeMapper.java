package io.ketlv.ediplomadapp.mapper;

import io.ketlv.ediplomadapp.domain.DiplomaType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.PathVariable;

@Mapper
public interface DiplomaTypeMapper {
    @Select("select * from diploma_type where name = #{name}")
    @ResultMap("diplomaTypeResultMap")
    DiplomaType findOneByName(@PathVariable @Param("name") String name);
}
