package io.ketlv.ediplomadapp.services.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PhoiPaging {
    private Long page;
    private Long pageSize;
}
