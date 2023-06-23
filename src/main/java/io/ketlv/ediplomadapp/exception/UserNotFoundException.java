package io.ketlv.ediplomadapp.exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String msg) {
        super(msg);
    }
}
