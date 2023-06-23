package io.ketlv.ediplomadapp.services.dto;

import io.ketlv.ediplomadapp.domain.AbstractAuditingEntity;
import io.ketlv.ediplomadapp.enumuration.DiplomaStatusEnum;
import io.ketlv.ediplomadapp.enumuration.ModeOfStudyEnum;
import io.ketlv.ediplomadapp.enumuration.SexEnum;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.annotations.Result;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
public class DiplomaDto extends AbstractAuditingEntity<Long> {
    private Long id;
    private Long graduationCatalogId;
    private String major;
    private String studentId;
    private String fullName;
    private Date dateOfBirth;
    private String placeOfOrigin;
    private SexEnum sex;
    private String indigenous;
    private String nationality;
    private String ranking;
    private Integer yearGraduation;
    private ModeOfStudyEnum modeOfStudy;
    private String donvi;
    private String serialNumber;
    private String referenceNumber;
    private String signer;
    private String signerTitle;
    private String foreignLanguage;
    private String levelForeignLanguage;
    private Date dateOfEnrollment;
    private Date dateOfGraduation;
    private String trainingCourse;
    private Date dateOfDefend;
    private String hoiDongThi;
    private String decisionNumber;
    private String decisionEstablishingCouncil;
    private String reqType;
    private Double gpa;
    private String diplomaType;
    private Double trainingPeriodSemester;
    private Integer totalCredits;
    private String diplomaLink;
    private String appendixLnk;
    private String note;
    private DiplomaStatusEnum status;

    @Override
    public Long getId() {
        return id;
    }

    public Long getGraduationCatalogId() {
        return graduationCatalogId;
    }

    public String getMajor() {
        return major;
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

    public String getIndigenous() {
        return indigenous;
    }

    public String getNationality() {
        return nationality;
    }

    public String getRanking() {
        return ranking;
    }

    public Integer getYearGraduation() {
        return yearGraduation;
    }

    public ModeOfStudyEnum getModeOfStudy() {
        return modeOfStudy;
    }

    public String getDonvi() {
        return donvi;
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

    public String getTrainingCourse() {
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

    public String getReqType() {
        return reqType;
    }

    public Double getGpa() {
        return gpa;
    }

    public String getDiplomaType() {
        return diplomaType;
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

    public String getNote() {
        return note;
    }

    public DiplomaStatusEnum getStatus() {
        return status;
    }
}
