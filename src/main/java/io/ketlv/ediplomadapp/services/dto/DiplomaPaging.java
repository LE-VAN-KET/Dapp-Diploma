package io.ketlv.ediplomadapp.services.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class DiplomaPaging {
    private Long page;
    private Long pageSize;
    private Long yearGraduation;
}
