package io.ketlv.ediplomadapp.services.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class PhoiListRes {
    private Long total;
    private List<PhoiDtoRes> data;
}
