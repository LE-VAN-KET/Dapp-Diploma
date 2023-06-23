package io.ketlv.ediplomadapp.exception;

import lombok.Getter;

@Getter
public class CommonBadRequest extends RuntimeException {
    public CommonBadRequest(String msg) {
        super(msg);
    }
}
