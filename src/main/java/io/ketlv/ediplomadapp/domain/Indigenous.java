package io.ketlv.ediplomadapp.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Indigenous  extends AbstractAuditingEntity<Long>{
    private Long indigenousId;
    private String indigenousName;

    public String getIndigenousName() {
        return indigenousName;
    }

    @Override
    public Long getId() {
        return indigenousId;
    }
}
