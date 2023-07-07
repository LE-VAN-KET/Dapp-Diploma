package io.ketlv.ediplomadapp.services.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class DiplomaSearchDto {
    private String fullName;
    private String dateOfBirth;
    private String serialNumber;
}
