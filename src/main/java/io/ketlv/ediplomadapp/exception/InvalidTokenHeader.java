package io.ketlv.ediplomadapp.exception;

import lombok.Getter;

@Getter
public class InvalidTokenHeader extends RuntimeException {
    public InvalidTokenHeader(String message) {
        super(message);
    }
}
