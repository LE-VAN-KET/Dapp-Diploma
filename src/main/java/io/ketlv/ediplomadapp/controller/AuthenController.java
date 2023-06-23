package io.ketlv.ediplomadapp.controller;

import io.ketlv.ediplomadapp.services.UserService;
import io.ketlv.ediplomadapp.services.dto.LoginRequest;
import io.ketlv.ediplomadapp.services.dto.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenController {
    private final UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<LoginResponse> signin(@Valid @RequestBody LoginRequest loginUserRequest) {
        return ResponseEntity.ok().body(userService.signin(loginUserRequest));
    }
}
