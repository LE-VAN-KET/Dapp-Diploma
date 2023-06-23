package io.ketlv.ediplomadapp.services.impl;

import io.ketlv.ediplomadapp.services.MailService;
import io.ketlv.ediplomadapp.services.dto.EmailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine htmlTemplateEngine;

    @Override
    public void sendMimeMessage(EmailDto emailDTO, Context ctx, String template) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        helper.setTo(emailDTO.getRecipient());
        helper.setSubject(emailDTO.getSubject());

        String htmlContent = htmlTemplateEngine.process(template, ctx);
        helper.setText(htmlContent, true);
        javaMailSender.send(message);
    }
}
