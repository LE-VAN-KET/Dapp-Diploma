package io.ketlv.ediplomadapp.services.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddUserReq {
    @NotNull
    @NotBlank
    private String id;
    @NotNull
    @NotBlank
    private String username;
    @NotBlank
    @NotNull
    private String email;
    @NotBlank
    @NotNull
    private String initPassword;
    @NotNull
    private Long authorityId;
    private String citizenIdentityNo;
    @NotBlank
    @NotNull
    private String fullName;
    private String phoneNo;
}
