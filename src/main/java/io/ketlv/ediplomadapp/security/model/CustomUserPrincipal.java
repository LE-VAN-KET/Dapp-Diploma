package io.ketlv.ediplomadapp.security.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUserPrincipal extends User {
    private String subId;
    private String auth;
    private String tokenType;
    private final String fullName;
    private String donviSymbol;

    public CustomUserPrincipal(String donviSymbol, String username, String subId, String fullName, String tokenType, String auth,
                               Collection<? extends GrantedAuthority> authorities) {
        super(username, "", true, true, true, true, authorities);
        this.fullName = fullName;
        this.tokenType = tokenType;
        this.auth = auth;
        this.subId = subId;
        this.donviSymbol = donviSymbol;
    }

    public CustomUserPrincipal(String donviSymbol, String username, String password, String subId, String fullName, boolean enabled,
                               Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, true, true, true, authorities);
        this.fullName = fullName;
        this.subId = subId;
        this.donviSymbol = donviSymbol;
    }

    public CustomUserPrincipal(String username, String subId, String fullName, boolean enabled,
                               Collection<? extends GrantedAuthority> authorities) {
        super(username, "", enabled, true, true, true, authorities);
        this.fullName = fullName;
        this.subId = subId;
    }

    public String getAuth() {
        return auth;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getFullName() {
        return fullName;
    }

    public String getSubId() {
        return subId;
    }

    public String getdonviSymbol() {
        return donviSymbol;
    }
}