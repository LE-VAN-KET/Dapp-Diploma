package io.ketlv.ediplomadapp.services.dto;

import io.ketlv.ediplomadapp.utils.validation.ValidPassword;
import io.ketlv.ediplomadapp.utils.validation.ValidUsername;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LoginRequest {
    @NotNull
    @NotEmpty(message = "Username is required")
    @ValidUsername
    private String username;

    @NotNull
    @NotEmpty(message = "Password is required")
    @ValidPassword
    private String password;
}
