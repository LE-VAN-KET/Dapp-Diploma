package io.ketlv.ediplomadapp.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Ward {
    private String wardZipcode;
    private String districtZipcode;
    private String name;
}
