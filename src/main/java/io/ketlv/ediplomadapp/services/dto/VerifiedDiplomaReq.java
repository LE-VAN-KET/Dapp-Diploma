package io.ketlv.ediplomadapp.services.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class VerifiedDiplomaReq {
    private List<Long> diplomaIds;
}
