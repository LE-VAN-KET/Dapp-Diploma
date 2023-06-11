package io.ketlv.ediplomadapp.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class DiplomaType {
    private String symbol;
    private String name;
    private String note;
}
