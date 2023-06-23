package io.ketlv.ediplomadapp.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CommonErrorResponse {
    private Integer code;
    private String message;

    public CommonErrorResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public CommonErrorResponse(HttpStatus status, String message) {
        this.code = status.value();
        this.message = message;
    }

    public CommonErrorResponse() {
    }
}
