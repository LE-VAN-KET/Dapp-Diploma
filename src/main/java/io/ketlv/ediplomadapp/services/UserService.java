package io.ketlv.ediplomadapp.services;

import io.ketlv.ediplomadapp.services.dto.AddUserReq;
import io.ketlv.ediplomadapp.services.dto.LoginRequest;
import io.ketlv.ediplomadapp.services.dto.LoginResponse;
import io.ketlv.ediplomadapp.services.dto.UserDto;

import javax.mail.MessagingException;
import java.util.List;

public interface UserService {
    void addUser(AddUserReq req);
    List<UserDto> getAll();
    LoginResponse signin(LoginRequest request);
}
