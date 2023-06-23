package io.ketlv.ediplomadapp.exception;

import lombok.Getter;

@Getter
public class LoadKeyStoreTokenException extends RuntimeException {

    public LoadKeyStoreTokenException(String message) {
        super(message);
    }
}
