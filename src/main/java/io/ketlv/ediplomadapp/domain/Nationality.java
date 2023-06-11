package io.ketlv.ediplomadapp.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Nationality extends AbstractAuditingEntity<Long>{
    private Long nationalityId;
    private String nationalityName;

    @Override
    public Long getId() {
        return nationalityId;
    }

    public String getNationalityName() {
        return nationalityName;
    }
}
