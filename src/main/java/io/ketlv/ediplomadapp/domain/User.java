package io.ketlv.ediplomadapp.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends AbstractAuditingEntity<String> {
    private String id;
    private String username;
    private String email;
    private String password;
    private String initPassword;
    private Timestamp firstLogin;
    private Long authorityId;
    private boolean verified;
    private boolean disable;

    @Override
    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getInitPassword() {
        return initPassword;
    }

    public Timestamp getFirstLogin() {
        return firstLogin;
    }

    public boolean isVerified() {
        return verified;
    }

    public boolean isDisable() {
        return disable;
    }

    public Long getAuthorityId() {
        return authorityId;
    }

}
