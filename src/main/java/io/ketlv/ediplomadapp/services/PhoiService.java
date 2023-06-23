package io.ketlv.ediplomadapp.services;

import io.ketlv.ediplomadapp.services.dto.PhoiDtoReq;
import io.ketlv.ediplomadapp.services.dto.PhoiListRes;

import java.util.List;

public interface PhoiService {
    void addPhoi(PhoiDtoReq phoiDto);
    void revokePhoi(Long id);
    void update(PhoiDtoReq phoiDto, Long id);
    PhoiListRes getAll(Long page, Long pageSize);
}
