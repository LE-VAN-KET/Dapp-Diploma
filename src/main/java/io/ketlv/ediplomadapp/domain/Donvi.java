package io.ketlv.ediplomadapp.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Donvi extends AbstractAuditingEntity<String> {
    private String symbol;
    private String name;
    private String addressStreet;
    private Long addressWardId;
    private Long addressDistrictId;

    @Override
    public String getId() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public Long getAddressWardId() {
        return addressWardId;
    }

    public Long getAddressDistrictId() {
        return addressDistrictId;
    }
}
