package io.ketlv.ediplomadapp.event;

import io.ketlv.ediplomadapp.services.MailService;
import io.ketlv.ediplomadapp.services.dto.EmailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;

@Component
public class SendPasswordToMailListener {
    @Autowired
    private MailService mailService;

    @Async
    @EventListener
    public void sendOtpConfirmResetPassword(OnSendPassToMailEvent event) throws MessagingException {
        String subject = "Send Your Password in the platform Capstone";
        EmailDto emailDTO = EmailDto.builder().recipient(event.getToMail())
                .subject(subject).body(String.valueOf(event.getInitPassword())).build();
        // set context password into template mail
        final Context ctx = new Context(LocaleContextHolder.getLocale());
        ctx.setVariable("password", emailDTO.getBody());
        mailService.sendMimeMessage(emailDTO, ctx, "PasswordTemplate");
    }
}
