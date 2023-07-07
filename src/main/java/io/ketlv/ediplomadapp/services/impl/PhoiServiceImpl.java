package io.ketlv.ediplomadapp.services.impl;

import io.ketlv.ediplomadapp.domain.Diploma;
import io.ketlv.ediplomadapp.domain.Phoi;
import io.ketlv.ediplomadapp.mapper.DiplomaMapper;
import io.ketlv.ediplomadapp.mapper.PhoiMapper;
import io.ketlv.ediplomadapp.security.model.CustomUserPrincipal;
import io.ketlv.ediplomadapp.services.PhoiService;
import io.ketlv.ediplomadapp.services.dto.PhoiDtoReq;
import io.ketlv.ediplomadapp.services.dto.PhoiDtoRes;
import io.ketlv.ediplomadapp.services.dto.PhoiListRes;
import io.ketlv.ediplomadapp.services.dto.PhoiPaging;
import io.ketlv.ediplomadapp.services.fabric.ChainCode;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@AllArgsConstructor
@Transactional
public class PhoiServiceImpl implements PhoiService {
    private final PhoiMapper phoiMapper;
    private final DiplomaMapper diplomaMapper;
    private final ChainCode chainCode;

    @Override
    public void addPhoi(PhoiDtoReq phoiDto) {
        String maxSerialNumber = phoiMapper.maxSerialNumber("DND." + phoiDto.getDiplomaTypeSymbol() + ".0",
                phoiDto.getDiplomaTypeSymbol());
        AtomicReference<Long> maxNumber = new AtomicReference<>(Long.parseLong(StringUtils.substringAfterLast(maxSerialNumber, ".")));
        long serialNumberEnd = maxNumber.get() + phoiDto.getSoluong();

        Phoi phoi = new Phoi();
        BeanUtils.copyProperties(phoiDto, phoi);

        // update serial number to diploma before insert phoi
        UpdatePropertiesPhoi(phoiDto, phoi, maxNumber, serialNumberEnd, phoi.getDiplomaTypeSymbol());
        phoiMapper.insert(phoi);
        List<Diploma> diplomaList = diplomaMapper.selectAllByRangeSerialNumber(phoi.getSerialNumberBegin(), phoi.getSerialNumberEnd());
        try {
            CustomUserPrincipal userPrincipal = (CustomUserPrincipal) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();
            chainCode.issueDiploma(diplomaList, "src/main/resources/connection-org1.yaml", userPrincipal.getSubId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void revokePhoi(Long id) {
        phoiMapper.revokePhoi(id);
    }

    @Override
    public PhoiListRes getAll(Long page, Long pageSize) {
        if (page != null) {
            if (page > 0) {
                page = pageSize * (page - 1);
            } else {
                page = 0L;
            }

        }
        return PhoiListRes.builder()
                .data(phoiMapper.getAll(PhoiPaging.builder().page(page).pageSize(pageSize).build()))
                .total(phoiMapper.count()).build();
    }

    @Override
    public void update(PhoiDtoReq phoiDto, Long id) {
        Phoi existPhoi = phoiMapper.findOneById(id);
        if (existPhoi != null) {
            Phoi phoi = new Phoi();
            if(phoiDto.getTitle() != null && !existPhoi.getTitle().equals(phoiDto.getTitle())) {
                phoi.setTitle(phoiDto.getTitle());
            }
            if((phoiDto.getDonviSymbol() != null && !phoiDto.getDonviSymbol().equals(existPhoi.getDonviSymbol()))
                    || (phoiDto.getDiplomaTypeSymbol() != null && !phoiDto.getDiplomaTypeSymbol().equals(existPhoi.getDiplomaTypeSymbol()))
                    || (existPhoi.getAmountIssuedNewPrint() != phoiDto.getSoluong())) {
                phoi.setDonviSymbol(phoiDto.getDonviSymbol());
                phoi.setDiplomaTypeSymbol(phoiDto.getDiplomaTypeSymbol());
                diplomaMapper.resetSerialNumber(existPhoi.getSerialNumberBegin(), existPhoi.getSerialNumberEnd());
                String maxSerialNumber = phoiMapper.maxSerialNumberDiffMyself("DND." + phoiDto.getDiplomaTypeSymbol() + ".0",
                        phoiDto.getDiplomaTypeSymbol(), existPhoi.getId());
                AtomicReference<Long> maxNumber = new AtomicReference<>(Long.parseLong(StringUtils.substringAfterLast(maxSerialNumber, ".")));
                long serialNumberEnd = maxNumber.get() + phoiDto.getSoluong();

                // update serial number to diploma
                UpdatePropertiesPhoi(phoiDto, phoi, maxNumber, serialNumberEnd, phoiDto.getDiplomaTypeSymbol());
            }

            if(phoiDto.getLoaiPhoi() != null &&!phoiDto.getLoaiPhoi().equals(existPhoi.getLoaiPhoi())) {
                phoi.setLoaiPhoi(phoiDto.getLoaiPhoi());
            }
            if(phoiDto.getNgayMua() != null && !phoiDto.getNgayMua().equals(existPhoi.getNgayMua())) {
                phoi.setNgayMua(phoiDto.getNgayMua());
            }
            if(phoiDto.getNgayNhapKho() != null && !phoiDto.getNgayNhapKho().equals(existPhoi.getNgayMua())) {
                phoi.setNgayNhapKho(phoiDto.getNgayNhapKho());
            }
            if(phoiDto.getNote() != null && !phoiDto.getNote().equals(existPhoi.getNote())) {
                phoi.setNote(phoiDto.getNote());
            }

            phoi.setId(id);
            phoi.setLastModifiedDate(Instant.now());
            phoiMapper.update(phoi);
        } else {
            throw new RuntimeException("Phôi không tồn tại.");
        }
    }

    private void UpdatePropertiesPhoi(PhoiDtoReq phoiDto, Phoi phoi, AtomicReference<Long> maxNumber, long serialNumberEnd, String diplomaTypeSymbol) {
        int max = (int) (maxNumber.get() + 1);
        String serial = String.valueOf(max);
        for (int idx = 0; idx < (7 - maxNumber.get().toString().length()); idx++) serial = '0' + serial;
        phoi.setSerialNumberBegin("DND." + phoiDto.getDiplomaTypeSymbol() + "." + serial);
        List<Diploma> diplomas = diplomaMapper.getAllByDonviAndDiplomaType(phoiDto.getDonviSymbol(), diplomaTypeSymbol);
        updateSerialDiploma(diplomas, maxNumber, serialNumberEnd, phoiDto.getDiplomaTypeSymbol());
        int unusedNumber = phoiDto.getSoluong() - diplomas.size();
        phoi.setAmountIssuedStudent(unusedNumber > 0 ? diplomas.size() : phoiDto.getSoluong());
        phoi.setAmountIssuedNewPrint(phoiDto.getSoluong());
        serial = String.valueOf(serialNumberEnd);
        for (int idx = 0; idx < (7 - String.valueOf(serialNumberEnd).length()); idx++) serial = '0' + serial;
        phoi.setSerialNumberEnd("DND." + phoiDto.getDiplomaTypeSymbol() + "." + serial);
        if (unusedNumber > 0) {
            phoi.setAmountUnused(unusedNumber);
        } else {
            phoi.setAmountUnused(0);
        }
    }

    private void updateSerialDiploma(List<Diploma> diplomas, AtomicReference<Long> maxNumber, long serialNumberEnd, String diplomaTypeSymbol) {
        if (!diplomas.isEmpty()) {
            maxNumber.set(maxNumber.get() + 1);
            diplomas.forEach(dip -> {
                if(maxNumber.get() > serialNumberEnd) {
                    return;
                }
                String serial = String.valueOf(maxNumber.get());
                for (int idx = 0; idx < (7 - maxNumber.get().toString().length()); idx++) serial = '0' + serial;
                diplomaMapper.updateSerialNumber("DND." + diplomaTypeSymbol + "."
                        + serial, dip.getReferenceNumber());
                maxNumber.getAndSet(maxNumber.get() + 1);
            });
        }
    }
}
