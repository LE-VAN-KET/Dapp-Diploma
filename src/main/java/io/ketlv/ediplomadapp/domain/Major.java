package io.ketlv.ediplomadapp.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Major extends AbstractAuditingEntity<String> {
    private String id;
    private String name;

    @Override
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
