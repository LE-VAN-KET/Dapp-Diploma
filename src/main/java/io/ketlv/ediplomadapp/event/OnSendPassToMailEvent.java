package io.ketlv.ediplomadapp.event;

import io.ketlv.ediplomadapp.services.dto.EmailDto;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class OnSendPassToMailEvent extends ApplicationEvent {
    private String toMail;
    private String initPassword;
    public OnSendPassToMailEvent(Object source, String toMail, String initPassword) {
        super(source);
        this.toMail = toMail;
        this.initPassword = initPassword;
    }
}
