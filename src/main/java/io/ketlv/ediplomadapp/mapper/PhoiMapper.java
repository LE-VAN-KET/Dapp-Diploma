package io.ketlv.ediplomadapp.mapper;

import io.ketlv.ediplomadapp.domain.Phoi;
import io.ketlv.ediplomadapp.services.dto.PhoiDtoRes;
import io.ketlv.ediplomadapp.services.dto.PhoiPaging;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PhoiMapper {
    void insert(Phoi phoi);

    @Delete("select revokePhoi(#{id})")
    void revokePhoi(@Param("id") Long id);

    void update(Phoi phoi);

    List<PhoiDtoRes> getAll(PhoiPaging phoiPaging);

    @Select("select COALESCE(max(serial_number_end), #{serialEndDefault}) from phoi where diploma_type_symbol = #{diplomaTypeSymbol}")
    String maxSerialNumber(@Param("serialEndDefault") String serialEndDefault, @Param("diplomaTypeSymbol") String diplomaTypeSymbol);

    @Select("select COALESCE(max(serial_number_end), #{serialEndDefault}) from phoi where id <> #{id} and diploma_type_symbol = #{diplomaTypeSymbol}")
    String maxSerialNumberDiffMyself(@Param("serialEndDefault") String serialEndDefault, @Param("diplomaTypeSymbol") String diplomaTypeSymbol,
                                     @Param("id") Long id);

    @Select("select * from phoi where id = #{id}")
    @ResultMap("resultMapPhoi")
    Phoi findOneById(@Param("id") Long id);

    @Select("SELECT count(1) FROM phoi")
    long count();

    @Update("update phoi set amount_broken = amount_broken + 1 where " +
            "serial_number_begin <= #{serialNumber} and serial_number_end >= #{serialNumber}")
    void increaseCountPhoiBroken(@Param("serialNumber") String serialNumber);

    @Update("update phoi set amount_broken = amount_broken - 1 where " +
            "serial_number_begin <= #{serialNumber} and serial_number_end >= #{serialNumber}")
    void decreaseCountPhoiBroken(@Param("serialNumber") String serialNumber);
}
