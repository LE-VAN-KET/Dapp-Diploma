package io.ketlv.ediplomadapp.exception;

import lombok.Getter;

@Getter
public class RetrievePrivateKeyException extends RuntimeException {

    public RetrievePrivateKeyException(String message) {
        super(message);
    }
}
