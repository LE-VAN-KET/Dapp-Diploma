package io.ketlv.ediplomadapp.services.impl;

import io.ketlv.ediplomadapp.domain.Diploma;
import io.ketlv.ediplomadapp.mapper.DiplomaMapper;
import io.ketlv.ediplomadapp.utils.ExcelUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExcelService {
    private final Logger log = LoggerFactory.getLogger(DiplomaServiceImpl.class);
    private final DiplomaMapper diplomaMapper;
    private final ExcelUtil  excelUtil;

    public void uploadListDiplomas(MultipartFile file, String schoolSymbol, String graduationCatalogId) throws IOException {
        List<Diploma> diplomaList = excelUtil.excelToDiplomas(file.getInputStream(), "Văn Bằng", schoolSymbol, graduationCatalogId);
        diplomaMapper.insertListDiploma(diplomaList);
    }
}
