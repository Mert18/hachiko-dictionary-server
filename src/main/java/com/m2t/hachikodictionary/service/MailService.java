package com.m2t.hachikodictionary.service;

import com.m2t.hachikodictionary.dto.account.RegistrationRequest;
import com.postmarkapp.postmark.Postmark;
import com.postmarkapp.postmark.client.ApiClient;
import com.postmarkapp.postmark.client.data.model.message.Message;
import com.postmarkapp.postmark.client.data.model.message.MessageResponse;
import com.postmarkapp.postmark.client.exception.PostmarkException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.logging.Logger;

@Service
public class MailService {
    private static final Logger logger = Logger.getLogger(MailService.class.getName());

    private String postmarkApiKey;
    @Value("${POSTMARK_API_KEY}")
    public void setPostmarkApiKey(String postmarkApiKey) {
        this.postmarkApiKey = postmarkApiKey;
    }

    public String sendConfirmationEmail(RegistrationRequest registrationRequest, String token) throws PostmarkException, IOException {
        String subject = "Welcome to Hachiko Dictionary | Confirm Your Registration";
        String mailContent = "Welcome to Hachiko Dictionary, " + registrationRequest.getUsername() + "!\n" +
                "You can use the link below to confirm your account.\n\n" +
                "https://hachikodictionary.com/confirm-email?token=" + token + "&email=" + registrationRequest.getEmail();

        ApiClient client = Postmark.getApiClient(postmarkApiKey);
        Message message = new Message("mert.uygur@gazi.edu.tr", registrationRequest.getEmail(), subject, mailContent);
        MessageResponse response = client.deliverMessage(message);
        logger.info("Confirmation mail sent to: {}" + registrationRequest.getEmail());
        return response.getMessage();
    }
}
