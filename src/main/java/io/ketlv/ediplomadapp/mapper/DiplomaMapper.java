package io.ketlv.ediplomadapp.mapper;

import io.ketlv.ediplomadapp.domain.Diploma;
import io.ketlv.ediplomadapp.services.dto.DiplomaDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Mapper
public interface DiplomaMapper {
    List<DiplomaDto> findAll();

    @Select("select * from diploma where id = #{id}}")
    @ResultMap("diplomaResultMap")
    Diploma findOneById(@PathVariable Long id);

    @Select("select * from diploma where serial_number = #{serialNumber}")
    @ResultMap("diplomaResultMap")
    Diploma findOneBySerialNumber(@PathVariable String serialNumber);

    Long insertDiploma(Diploma diploma);
    void insertListDiploma(List<Diploma> diploma);
}
