package io.ketlv.ediplomadapp.mapper;

import io.ketlv.ediplomadapp.domain.UserInformation;
import io.ketlv.ediplomadapp.services.dto.AddUserReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserInformationMapper {
    void addUserInfo(AddUserReq addUserReq);

    @Select("select COUNT(1) from user_information where lower(citizen_identity_no) = lower(#{citizenIdentityNo})")
    boolean isExistCitizenIdentityNo(@Param("citizenIdentityNo") String citizenIdentityNo);

    @Select("select COUNT(1) from user_information where lower(phone_no) = lower(#{phoneNumber})")
    boolean isExistPhoneNo(@Param("phoneNumber") String phoneNumber);
}
