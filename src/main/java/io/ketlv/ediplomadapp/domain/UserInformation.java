package io.ketlv.ediplomadapp.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInformation extends AbstractAuditingEntity<String> {
    private String userId;
    private String citizenIdentityNo;
    private String fullName;
    private String phoneNo;
    private boolean disable;

    @Override
    public String getId() {
        return userId;
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

    public boolean isDisable() {
        return disable;
    }
}
