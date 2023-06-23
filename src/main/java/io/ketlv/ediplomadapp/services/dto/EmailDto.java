package io.ketlv.ediplomadapp.services.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EmailDto {
    private String recipient;
    private String subject;
    private String body;
}
