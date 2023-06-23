package io.ketlv.ediplomadapp.services.dto;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Builder
@Getter
public class LoginResponse implements Serializable {
    private String userId;
    private String donviSymbol;
    private String accessToken;
    private String tokenType;
    private Long expiresIn;
    private String refreshToken;
    private Long refreshExpiresIn;
    private String role;
}
