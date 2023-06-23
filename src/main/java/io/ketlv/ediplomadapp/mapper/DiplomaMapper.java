package io.ketlv.ediplomadapp.mapper;

import io.ketlv.ediplomadapp.domain.Diploma;
import io.ketlv.ediplomadapp.services.dto.DiplomaDto;
import io.ketlv.ediplomadapp.services.dto.DiplomaPaging;
import io.ketlv.ediplomadapp.services.dto.UpdateDiplomaReq;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DiplomaMapper {
    List<DiplomaDto> findAll(DiplomaPaging diplomaPaging);
    @Select("select * from diploma where donvi_symbol = #{donviSymbol} and diploma_type_symbol = #{diplomaTypeSymbol} " +
            "and status = 'VERIFIED' and serial_number isnull")
    @ResultMap("diplomaResultMap")
    List<Diploma> getAllByDonviAndDiplomaType(@Param("donviSymbol") String donviSymbol,
                                              @Param("diplomaTypeSymbol") String diplomaTypeSymbol);

    @Select("select * from diploma where id = #{id}")
    @ResultMap("diplomaResultMap")
    Diploma findOneById(@Param("id") Long id);

    @Select("select * from diploma where serial_number = #{serialNumber}")
    @ResultMap("diplomaResultMap")
    Diploma findOneBySerialNumber(@Param("serialNumber") String serialNumber);

    @Select("select * from diploma where reference_number = #{refNumber}")
    @ResultMap("diplomaResultMap")
    Diploma findOneByRefNumber(@Param("refNumber") String refNumber);

    Long insertDiploma(Diploma diploma);
    void insertListDiploma(List<Diploma> diploma);

    @Update("Update diploma set serial_number = #{serialNumber} where reference_number = #{referenceNumber}")
    void updateSerialNumber(@Param("serialNumber") String serialNumber, @Param("referenceNumber") String referenceNumber);

    @Update("Update diploma set serial_number = NULL where serial_number >= #{serialNumberBegin} and serial_number <= #{serialNumberEnd}")
    void resetSerialNumber(@Param("serialNumberBegin") String serialNumberBegin, @Param("serialNumberEnd") String serialNumberEnd);

    void verifiedDiploma(List<Long> ids);

    @Update("update diploma set status=#{status} where reference_number=#{refNumber}")
    void updateStatus(@Param("status") String status, @Param("refNumber") String refNumber);

    @Select("select count(0) from diploma where id = #{id}")
    boolean isExistById(@Param("id") Long id);

    void partialUpdate(UpdateDiplomaReq req);
    @Select("SELECT count(1) FROM diploma where (#{yearGraduation} = 0 or year_graduation = #{yearGraduation})")
    long count(Long yearGraduation);

    @Select("select count(0) from diploma where reference_number = #{ref}")
    boolean isExistByRefNo(@Param("ref") String ref);

    @Select("select DISTINCT year_graduation from diploma")
    List<Long> selectListYearGraduation();

    @Update("select * from diploma where serial_number >= #{serialNumberBegin} and serial_number <= #{serialNumberEnd}")
    @ResultMap("diplomaResultMap")
    List<Diploma> selectAllByRangeSerialNumber(@Param("serialNumberBegin") String serialNumberBegin, @Param("serialNumberEnd") String serialNumberEnd);
}
