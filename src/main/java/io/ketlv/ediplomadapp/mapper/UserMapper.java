package io.ketlv.ediplomadapp.mapper;

import io.ketlv.ediplomadapp.domain.User;
import io.ketlv.ediplomadapp.services.dto.AddUserReq;
import io.ketlv.ediplomadapp.services.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {
    void addUser(User user);
    @Select("select COUNT(1) from \"user\" where lower(username)=lower(#{username})")
    boolean isExistUsername(@Param("username") String username);

    @Select("select COUNT(1) from \"user\" where lower(email) = lower(#{email})")
    boolean isExistEmail(@Param("email") String email);

    @Select("select COUNT(1) from \"user\" where lower(id) = lower(#{id})")
    boolean isExistID(@Param("id") String id);


    UserDto findByUsernameEqualsIgnoreCase(@Param("username") String username);

    List<UserDto> getAll();
}
