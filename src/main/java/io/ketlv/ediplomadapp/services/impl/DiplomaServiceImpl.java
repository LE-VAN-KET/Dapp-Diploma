package io.ketlv.ediplomadapp.services.impl;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;
import io.ketlv.ediplomadapp.config.IPFSConfig;
import io.ketlv.ediplomadapp.constants.NetworkConfig;
import io.ketlv.ediplomadapp.domain.Diploma;
import io.ketlv.ediplomadapp.enumuration.DiplomaStatusEnum;
import io.ketlv.ediplomadapp.mapper.DiplomaMapper;
import io.ketlv.ediplomadapp.mapper.PhoiMapper;
import io.ketlv.ediplomadapp.services.DiplomaService;
import io.ketlv.ediplomadapp.services.dto.*;
import io.ketlv.ediplomadapp.services.fabric.ChainCode;
import liquibase.pro.packaged.di;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiplomaServiceImpl implements DiplomaService {
    private final Logger log = LoggerFactory.getLogger(DiplomaServiceImpl.class);
    private final DiplomaMapper diplomaMapper;
    private final IPFSConfig ipfsConfig;
    private final PhoiMapper phoiMapper;

    @Override
    public Diploma save(Diploma diploma) {
        diplomaMapper.insertDiploma(diploma);
        Diploma dip = findOneBySerialNumber(diploma.getSerialNumber());
        if (dip != null) {
            try {
                ChainCode.issueDiploma(dip, NetworkConfig.networkConfigPathOrgIssuer);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return dip;
    }

    @Override
    public void partialUpdate(UpdateDiplomaReq req, MultipartFile file) {
        log.debug("Request to partially update Diploma : {}", req);
        boolean isExist = diplomaMapper.isExistById(req.getId());
        if (isExist) {
            try {
                if (file != null) {
                    InputStream inputStream = new ByteArrayInputStream(file.getBytes());
                    IPFS ipfs = ipfsConfig.ipfs;

                    NamedStreamable.InputStreamWrapper is = new NamedStreamable.InputStreamWrapper(inputStream);
                    MerkleNode response = ipfs.add(is).get(0);
                    req.setDiplomaLink(response.hash.toBase58());
                }
                diplomaMapper.partialUpdate(req);
            } catch (IOException ex) {
                log.error("[ERROR] save file diploma failed.", ex);
                throw new RuntimeException(ex.getMessage());
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DiplomasRes findAll(Long page, Long size, Long yearGraduation) {
        if (page != null) {
            if (page > 0) {
                page = size * (page - 1);
            } else {
                page = 0L;
            }
        }
        long count = 0;
        if (yearGraduation != null) {
            count = diplomaMapper.count(yearGraduation);
        } else {
            count = diplomaMapper.count(0L);
        }
        return DiplomasRes.builder()
                .data(diplomaMapper.findAll(new DiplomaPaging(page, size, yearGraduation)))
                .total(count)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public Diploma findOne(Long id) {
        log.debug("Request to get Diploma : {}", id);
        return diplomaMapper.findOneById(id);
    }

    @Override
    public void delete(Long id) {
//        log.debug("Request to delete Diploma : {}", id);
//        diplomaRepository.deleteById(id);
    }

    @Override
    public Diploma findOneBySerialNumber(String serialNumber) {
        return diplomaMapper.findOneBySerialNumber(serialNumber);
    }

    @Override
    public void verifiedDiplomas(VerifiedDiplomaReq req) {
        diplomaMapper.verifiedDiploma(req.getDiplomaIds());
    }

    @Override
    public void updateStatus(DiplomaStatusReq req, String refNumber) {
        Diploma dipExist = diplomaMapper.findOneByRefNumber(refNumber);
        if (dipExist != null) {
            diplomaMapper.updateStatus(req.getStatus().toString(), refNumber);
            if (dipExist.getSerialNumber() != null && !"".equals(dipExist.getSerialNumber())) {
                if (req.getStatus().equals(DiplomaStatusEnum.DAHONG) &&
                        !dipExist.getStatus().equals(DiplomaStatusEnum.DAHONG)) {
                    phoiMapper.increaseCountPhoiBroken(dipExist.getSerialNumber());
                } else if (!req.getStatus().equals(DiplomaStatusEnum.DAHONG) &&
                        dipExist.getStatus().equals(DiplomaStatusEnum.DAHONG)) {
                    phoiMapper.decreaseCountPhoiBroken(dipExist.getSerialNumber());
                }
            }
        }

    }

    @Override
    public byte[] loadFileDiploma(String hash) {
        try {
            IPFS ipfs = ipfsConfig.ipfs;
            Multihash filePointer = Multihash.fromBase58(hash);
            return ipfs.cat(filePointer);
        } catch (IOException ex) {
            throw new RuntimeException("Error white communicating with the IPFS node", ex);
        }
    }

    @Override
    public List<Long> getListYearGraduation() {
        return diplomaMapper.selectListYearGraduation();
    }
}
