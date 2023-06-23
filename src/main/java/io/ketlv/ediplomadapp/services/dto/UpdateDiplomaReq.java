package io.ketlv.ediplomadapp.services.dto;

import io.ketlv.ediplomadapp.enumuration.ModeOfStudyEnum;
import io.ketlv.ediplomadapp.enumuration.SexEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateDiplomaReq {
    private Long id;
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
    private Long reqTypeId;
    private Double gpa;
    private String diplomaTypeSymbol;
    private Double trainingPeriodSemester;
    private Integer totalCredits;
    private String note;
    private String diplomaLink;
}
