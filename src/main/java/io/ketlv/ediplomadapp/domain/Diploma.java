package io.ketlv.ediplomadapp.domain;

import io.ketlv.ediplomadapp.enumuration.DiplomaStatusEnum;
import io.ketlv.ediplomadapp.enumuration.ModeOfStudyEnum;
import io.ketlv.ediplomadapp.enumuration.SexEnum;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Diploma extends AbstractAuditingEntity<Long> {
    private Long id;
    private Long graduationCatalogId;
    private String majorId;
    private String studentId;
    private String fullName;
    private Date dateOfBirth;
    private String placeOfOrigin;
    private SexEnum sex;
    private Long indigenousId;
    private Long nationalityId;
    private Long rankingId;
    private Integer yearGraduation;
    private ModeOfStudyEnum modeOfStudy;
    private String donviSymbol;
    private String serialNumber;
    private String referenceNumber;
    private String signer;
    private String signerTitle;
    private String foreignLanguage;
    private String levelForeignLanguage;
    private Date dateOfEnrollment;
    private Date dateOfGraduation;
    private Integer trainingCourse;
    private Date dateOfDefend;
    private String hoiDongThi;
    private String decisionNumber;
    private String decisionEstablishingCouncil;
    private Long reqTypeId;
    private Double gpa;
    private String diplomaTypeSymbol;
    private Double trainingPeriodSemester;
    private Integer totalCredits;
    private String diplomaLink;
    private String appendixLnk;
    private String hash;
    private String transactionId;
    private String note;
    private DiplomaStatusEnum status;

    @Override
    public Long getId() {
        return id;
    }

    public Long getGraduationCatalogId() {
        return graduationCatalogId;
    }

    public String getMajorId() {
        return majorId;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getFullName() {
        return fullName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPlaceOfOrigin() {
        return placeOfOrigin;
    }

    public SexEnum getSex() {
        return sex;
    }

    public Long getIndigenousId() {
        return indigenousId;
    }

    public Long getNationalityId() {
        return nationalityId;
    }

    public Long getRankingId() {
        return rankingId;
    }

    public Integer getYearGraduation() {
        return yearGraduation;
    }

    public ModeOfStudyEnum getModeOfStudy() {
        return modeOfStudy;
    }

    public String getDonviSymbol() {
        return donviSymbol;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public String getSigner() {
        return signer;
    }

    public String getSignerTitle() {
        return signerTitle;
    }

    public String getForeignLanguage() {
        return foreignLanguage;
    }

    public String getLevelForeignLanguage() {
        return levelForeignLanguage;
    }

    public Date getDateOfEnrollment() {
        return dateOfEnrollment;
    }

    public Date getDateOfGraduation() {
        return dateOfGraduation;
    }

    public Integer getTrainingCourse() {
        return trainingCourse;
    }

    public Date getDateOfDefend() {
        return dateOfDefend;
    }

    public String getHoiDongThi() {
        return hoiDongThi;
    }

    public String getDecisionNumber() {
        return decisionNumber;
    }

    public String getDecisionEstablishingCouncil() {
        return decisionEstablishingCouncil;
    }

    public Long getReqTypeId() {
        return reqTypeId;
    }

    public Double getGpa() {
        return gpa;
    }

    public String getDiplomaTypeSymbol() {
        return diplomaTypeSymbol;
    }

    public Double getTrainingPeriodSemester() {
        return trainingPeriodSemester;
    }

    public Integer getTotalCredits() {
        return totalCredits;
    }

    public String getDiplomaLink() {
        return diplomaLink;
    }

    public String getAppendixLnk() {
        return appendixLnk;
    }

    public String getHash() {
        return hash;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getNote() {
        return note;
    }

    public DiplomaStatusEnum getStatus() {
        return status;
    }
}
