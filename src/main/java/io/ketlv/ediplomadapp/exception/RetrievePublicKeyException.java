package io.ketlv.ediplomadapp.exception;

import lombok.Getter;

@Getter
public class RetrievePublicKeyException extends RuntimeException {
    public RetrievePublicKeyException(String message) {
        super( message);
    }
}
