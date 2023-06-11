package io.ketlv.ediplomadapp.services.impl;

import io.ketlv.ediplomadapp.constants.NetworkConfig;
import io.ketlv.ediplomadapp.domain.Diploma;
import io.ketlv.ediplomadapp.mapper.DiplomaMapper;
import io.ketlv.ediplomadapp.services.DiplomaService;
import io.ketlv.ediplomadapp.services.dto.DiplomaDto;
import io.ketlv.ediplomadapp.services.fabric.ChainCode;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiplomaServiceImpl implements DiplomaService {
    private final Logger log = LoggerFactory.getLogger(DiplomaServiceImpl.class);
    private final DiplomaMapper diplomaMapper;

//    @Override
//    public Diploma save(Diploma diploma) {
//        log.debug("Request to save Diploma : {}", diploma);
//        return diplomaMapper.save(diploma);
//    }

//    @Override
//    public Diploma update(Diploma diploma) {
//        log.debug("Request to update Diploma : {}", diploma);
//        return diplomaRepository.save(diploma);
//    }

//    @Override
//    public Optional<Diploma> partialUpdate(Diploma diploma) {
//        log.debug("Request to partially update Diploma : {}", diploma);
//
//        return diplomaRepository
//                .findById(diploma.getId())
//                .map(existingDiploma -> {
//                    if (diploma.getGraduationCatalogId() != null) {
//                        existingDiploma.setGraduationCatalogId(diploma.getGraduationCatalogId());
//                    }
//                    if (diploma.getMajorId() != null) {
//                        existingDiploma.setMajorId(diploma.getMajorId());
//                    }
//                    if (diploma.getStudentId() != null) {
//                        existingDiploma.setStudentId(diploma.getStudentId());
//                    }
//                    if (diploma.getFullName() != null) {
//                        existingDiploma.setFullName(diploma.getFullName());
//                    }
//                    if (diploma.getDateOfBirth() != null) {
//                        existingDiploma.setDateOfBirth(diploma.getDateOfBirth());
//                    }
//                    if (diploma.getPlaceOfOrigin() != null) {
//                        existingDiploma.setPlaceOfOrigin(diploma.getPlaceOfOrigin());
//                    }
//                    if (diploma.getSex() != null) {
//                        existingDiploma.setSex(diploma.getSex());
//                    }
//                    if (diploma.getIndigenousId() != null) {
//                        existingDiploma.setIndigenousId(diploma.getIndigenousId());
//                    }
//                    if (diploma.getNationalityId() != null) {
//                        existingDiploma.setNationalityId(diploma.getNationalityId());
//                    }
//                    if (diploma.getRankingId() != null) {
//                        existingDiploma.setRankingId(diploma.getRankingId());
//                    }
//                    if (diploma.getYearGraduation() != null) {
//                        existingDiploma.setYearGraduation(diploma.getYearGraduation());
//                    }
//                    if (diploma.getModeOfStudy() != null) {
//                        existingDiploma.setModeOfStudy(diploma.getModeOfStudy());
//                    }
//                    if (diploma.getDonviSymbol() != null) {
//                        existingDiploma.setDonviSymbol(diploma.getDonviSymbol());
//                    }
//                    if (diploma.getSerialNumber() != null) {
//                        existingDiploma.setSerialNumber(diploma.getSerialNumber());
//                    }
//                    if (diploma.getReferenceNumber() != null) {
//                        existingDiploma.setReferenceNumber(diploma.getReferenceNumber());
//                    }
//                    if (diploma.getSigner() != null) {
//                        existingDiploma.setSigner(diploma.getSigner());
//                    }
//                    if (diploma.getSignerTitle() != null) {
//                        existingDiploma.setSignerTitle(diploma.getSignerTitle());
//                    }
//                    if (diploma.getForeignLanguage() != null) {
//                        existingDiploma.setForeignLanguage(diploma.getForeignLanguage());
//                    }
//                    if (diploma.getLevelForeignLanguage() != null) {
//                        existingDiploma.setLevelForeignLanguage(diploma.getLevelForeignLanguage());
//                    }
//                    if (diploma.getDateOfEnrollment() != null) {
//                        existingDiploma.setDateOfEnrollment(diploma.getDateOfEnrollment());
//                    }
//                    if (diploma.getDateOfGraduation() != null) {
//                        existingDiploma.setDateOfGraduation(diploma.getDateOfGraduation());
//                    }
//                    if (diploma.getTrainingCourse() != null) {
//                        existingDiploma.setTrainingCourse(diploma.getTrainingCourse());
//                    }
//                    if (diploma.getDateOfDefend() != null) {
//                        existingDiploma.setDateOfDefend(diploma.getDateOfDefend());
//                    }
//                    if (diploma.getHoiDongThi() != null) {
//                        existingDiploma.setHoiDongThi(diploma.getHoiDongThi());
//                    }
//                    if (diploma.getDecisionNumber() != null) {
//                        existingDiploma.setDecisionNumber(diploma.getDecisionNumber());
//                    }
//                    if (diploma.getDecisionEstablishingCouncil() != null) {
//                        existingDiploma.setDecisionEstablishingCouncil(diploma.getDecisionEstablishingCouncil());
//                    }
//                    if (diploma.getReqTypeId() != null) {
//                        existingDiploma.setReqTypeId(diploma.getReqTypeId());
//                    }
//                    if (diploma.getGpa() != null) {
//                        existingDiploma.setGpa(diploma.getGpa());
//                    }
//                    if (diploma.getDiplomaTypeSymbol() != null) {
//                        existingDiploma.setDiplomaTypeSymbol(diploma.getDiplomaTypeSymbol());
//                    }
//                    if (diploma.getTrainingPeriodSemester() != null) {
//                        existingDiploma.setTrainingPeriodSemester(diploma.getTrainingPeriodSemester());
//                    }
//                    if (diploma.getTotalCredits() != null) {
//                        existingDiploma.setTotalCredits(diploma.getTotalCredits());
//                    }
//                    if (diploma.getDiplomaLink() != null) {
//                        existingDiploma.setDiplomaLink(diploma.getDiplomaLink());
//                    }
//                    if (diploma.getAppendixLnk() != null) {
//                        existingDiploma.setAppendixLnk(diploma.getAppendixLnk());
//                    }
//                    if (diploma.getHash() != null) {
//                        existingDiploma.setHash(diploma.getHash());
//                    }
//                    if (diploma.getTransactionId() != null) {
//                        existingDiploma.setTransactionId(diploma.getTransactionId());
//                    }
//                    if (diploma.getNote() != null) {
//                        existingDiploma.setNote(diploma.getNote());
//                    }
//                    if (diploma.getStatus() != null) {
//                        existingDiploma.setStatus(diploma.getStatus());
//                    }
//
//                    return existingDiploma;
//                })
//                .map(diplomaRepository::save);
//    }

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
    public Diploma update(Diploma diploma) {
        return null;
    }

    @Override
    public Optional<Diploma> partialUpdate(Diploma diploma) {
        return Optional.empty();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiplomaDto> findAll() {
        log.debug("Request to get all Diplomas");
        return diplomaMapper.findAll();
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
}
