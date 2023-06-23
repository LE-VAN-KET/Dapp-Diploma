package io.ketlv.ediplomadapp.services.dto;

import io.ketlv.ediplomadapp.enumuration.DiplomaStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DiplomaStatusReq {
    @NotNull
    private DiplomaStatusEnum status;
}
