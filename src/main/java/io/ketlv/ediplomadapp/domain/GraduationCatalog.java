package io.ketlv.ediplomadapp.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GraduationCatalog extends AbstractAuditingEntity<Long> {
    private Long id;
    @NotNull
    @NotEmpty(message = "Field title must be not empty.")
    private String title;
    @NotNull
    @NotEmpty(message = "Field decision graduation must be not empty.")
    private String decisionGraduation;
    private Integer yearGraduation;
    private String content;
    private String note;

    @Override
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDecisionGraduation() {
        return decisionGraduation;
    }

    public Integer getYearGraduation() {
        return yearGraduation;
    }

    public String getContent() {
        return content;
    }

    public String getNote() {
        return note;
    }
}
