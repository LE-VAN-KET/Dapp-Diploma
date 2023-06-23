package io.ketlv.ediplomadapp.services.dto;

import io.ketlv.ediplomadapp.domain.GraduationCatalog;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CatalogsRes {
    private Long total;
    private List<GraduationCatalog> data;
}
