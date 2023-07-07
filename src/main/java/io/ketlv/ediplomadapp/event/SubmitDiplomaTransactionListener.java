package io.ketlv.ediplomadapp.event;

import io.ketlv.ediplomadapp.domain.Diploma;
import io.ketlv.ediplomadapp.mapper.DiplomaMapper;
import org.hyperledger.fabric.gateway.ContractException;
import org.hyperledger.fabric.gateway.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component
public class SubmitDiplomaTransactionListener {
    @Autowired
    private DiplomaMapper diplomaMapper;
    @Async
    @EventListener
    public void submitTransactionDiplomaToBlockchain(SubmitDiplomaTransactionEvent event) {
        try {
            Diploma diploma = event.getDiploma();
            Transaction transaction = event.getContract().createTransaction(event.getName());

            byte[] res = transaction.submit(diploma.getGraduationCatalogId().toString(), diploma.getMajorId(),
                    diploma.getStudentId(), diploma.getFullName(),
                    String.format("%tY-%tm-%td", diploma.getDateOfBirth(), diploma.getDateOfBirth(), diploma.getDateOfBirth()), diploma.getPlaceOfOrigin(),
                    diploma.getSex().toString(), diploma.getIndigenousId().toString(), diploma.getNationalityId().toString(), diploma.getRankingId().toString(),
                    diploma.getYearGraduation().toString(), String.valueOf(diploma.getModeOfStudy()), diploma.getDonviSymbol(),
                    diploma.getSerialNumber(), diploma.getReferenceNumber(), diploma.getSigner(), diploma.getSignerTitle(),
                    diploma.getForeignLanguage() != null ? diploma.getForeignLanguage() : "",
                    diploma.getLevelForeignLanguage() != null ? diploma.getLevelForeignLanguage() : "",
                    diploma.getDateOfEnrollment() != null ?  String.format("%tY-%tm-%td", diploma.getDateOfEnrollment(), diploma.getDateOfEnrollment(), diploma.getDateOfEnrollment()) : "",
                    diploma.getDateOfGraduation() != null ? String.format("%tY-%tm-%td", diploma.getDateOfGraduation(), diploma.getDateOfGraduation(), diploma.getDateOfGraduation()) : "",
                    diploma.getTrainingCourse().toString(),
                    diploma.getDateOfDefend() != null ? String.format("%tY-%tm-%td", diploma.getDateOfDefend(), diploma.getDateOfDefend(), diploma.getDateOfDefend()) : "",
                    diploma.getHoiDongThi() != null ? diploma.getHoiDongThi() : "",
                    diploma.getDecisionNumber() != null ? diploma.getDecisionNumber() : "",
                    diploma.getDecisionEstablishingCouncil() != null ? diploma.getDecisionEstablishingCouncil() : "",
                    diploma.getReqTypeId().toString(), diploma.getGpa() != null ? diploma.getGpa().toString() : "",
                    diploma.getDiplomaTypeSymbol(),
                    diploma.getTrainingPeriodSemester() != null ? diploma.getTrainingPeriodSemester().toString() : "",
                    diploma.getTotalCredits() != null ? diploma.getTotalCredits().toString() : "",
                    diploma.getDiplomaLink() != null ? diploma.getDiplomaLink() : "",
                    diploma.getNote() != null ? diploma.getNote() : "", diploma.getStatus().toString(),
                    diploma.getCreatedBy() != null ? diploma.getCreatedBy() : "", diploma.getCreatedDate().toString(),
                    diploma.getLastModifiedDate().toString());
            System.out.println(new String(res, StandardCharsets.UTF_8));
            diplomaMapper.updateTransaction(diploma.getSerialNumber(), transaction.getTransactionId());
        } catch (ContractException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
