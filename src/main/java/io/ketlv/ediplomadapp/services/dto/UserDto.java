package io.ketlv.ediplomadapp.services.dto;

import io.ketlv.ediplomadapp.domain.AbstractAuditingEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
public class UserDto extends AbstractAuditingEntity<String> {
    private String id;

    private String username;

    private String email;
    private String password;

    private String initPassword;

    private Long authorityId;
    private String authorityName;
    private String citizenIdentityNo;

    private String fullName;
    private String phoneNo;

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

    public Long getAuthorityId() {
        return authorityId;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public String getCitizenIdentityNo() {
        return citizenIdentityNo;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }
}
