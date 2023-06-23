package io.ketlv.ediplomadapp.services;

import io.ketlv.ediplomadapp.services.dto.EmailDto;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;

public interface MailService {
    void sendMimeMessage(EmailDto emailDTO, Context ctx, String template) throws MessagingException;
}
