package com.m2t.hachikodictionary.service;

import com.m2t.hachikodictionary.dto.RegistrationRequest;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class MailService {
    private static final Logger logger = Logger.getLogger(MailService.class.getName());
    private String sendGridApiKey;

    @Value("${sendgrid.api.key}")
    public void setSendGridApiKey(String sendGridApiKey) {
        this.sendGridApiKey = sendGridApiKey;
    }

    public String sendConfirmationEmail(RegistrationRequest registrationRequest, String token) {
        Email from = new Email("mertplayschess@outlook.com");
        String subject = "Welcome to Hachiko Dictionary";
        Email to = new Email(registrationRequest.getEmail());
        Content content = new Content("text/plain",
                "Welcome to Hachiko Dictionary, " + registrationRequest.getUsername() + "!\n" +
                        "You can use the link below to confirm your account.\n\n" +
                        "http://localhost:3000/confirm-email?token=" + token + "&email=" + registrationRequest.getEmail());
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();
        try {
            request.setMethod(com.sendgrid.Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Error sending email: " + e.getMessage());
        }
    }
}
