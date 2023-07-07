package io.ketlv.ediplomadapp.services.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DiplomaQueryRes {
    private String key;
    private DiplomaResSearch record;
}
