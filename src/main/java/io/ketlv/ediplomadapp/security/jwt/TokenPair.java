package io.ketlv.ediplomadapp.security.jwt;

import lombok.*;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class TokenPair implements Serializable {
    private String accessToken;
    private String refreshToken;
}