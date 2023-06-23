package io.ketlv.ediplomadapp.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.ketlv.ediplomadapp.config.properties.TokenProperties;
import io.ketlv.ediplomadapp.security.TokenKey;
import io.ketlv.ediplomadapp.security.TokenProvider;
import io.ketlv.ediplomadapp.security.model.CustomUserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.stream.Collectors;


/*
 * @author: levanket
 * @description: Class provide generate and validate sign token
 * @update:
 **/
@Slf4j
@Component
public class TokenCreator {

    @Autowired
    private TokenProvider tokenProvider;

    /**
     * Generating a JWT
     * @param authentication Authentication
     * @param tokenType token type(ACCESS OR REFRESH TOKEN)
     * @return jwt
     * */
    private String generateToken(Authentication authentication, String tokenType) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        CustomUserPrincipal customUserPrincipal = (CustomUserPrincipal) authentication.getPrincipal();

        Long tokenValidityInSeconds = tokenType.equals(TokenKey.ACCESS) ?
                TokenProperties.accessTokenValidityInSeconds : TokenProperties.refreshTokenValidityInSeconds;

        LocalDateTime validity = LocalDateTime.now().plusSeconds(tokenValidityInSeconds);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(TokenKey.AUTHORITIES, authorities)
                .claim(TokenKey.TOKEN_TYPE, tokenType)
                .claim(TokenKey.FULL_NAME, customUserPrincipal.getFullName())
                .claim(TokenKey.SUB_ID, customUserPrincipal.getSubId())
                .claim(TokenKey.DONVI_SYMBOL, customUserPrincipal.getdonviSymbol())
                .signWith(SignatureAlgorithm.RS256, tokenProvider.getPrivateKey())
                .setExpiration(Date.from(validity.atZone(ZoneId.systemDefault()).toInstant()))
                .compact();
    }

    public TokenPair createTokenPair(Authentication authentication) {
        return TokenPair.builder()
                .accessToken(generateToken(authentication, "ACCESS"))
                .refreshToken(generateToken(authentication, "REFRESH")).build();
    }

}
