package io.ketlv.ediplomadapp.controller;

import io.ketlv.ediplomadapp.services.UserService;
import io.ketlv.ediplomadapp.services.dto.AddUserReq;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> addUser(@Valid @RequestBody AddUserReq req) {
        userService.addUser(req);
        return new ResponseEntity<>("Create user is successfully.", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }
}
