package com.m2t.hachikodictionary.service;

import com.m2t.hachikodictionary.dto.ConfirmEmailRequest;
import com.m2t.hachikodictionary.dto.Response;
import com.m2t.hachikodictionary.exception.ConfirmationNotFoundException;
import com.m2t.hachikodictionary.model.Confirmation;
import com.m2t.hachikodictionary.repository.ConfirmationRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ConfirmationService {

    private final ConfirmationRepository confirmationRepository;

    public ConfirmationService(ConfirmationRepository confirmationRepository) {
        this.confirmationRepository = confirmationRepository;
    }

    public Boolean existsByEmail(String email) {
        return confirmationRepository.existsByEmail(email);
    }

    public void deleteById(String confirmationId) {
        confirmationRepository.deleteById(confirmationId);
    }

    public String create(String email) {
        String token = UUID.randomUUID().toString();
        Confirmation confirmation = new Confirmation(email, token);
        try {
            confirmationRepository.save(confirmation);
        } catch (Exception e) {
            throw new RuntimeException("Error creating confirmation: " + e.getMessage());
        }
        return token;
    }

    public Response confirmEmail(ConfirmEmailRequest confirmEmailRequest) {
        try {
            Confirmation confirmation = confirmationRepository.findConfirmationByEmail(confirmEmailRequest.getEmail());

            if(confirmation == null) {
                throw new ConfirmationNotFoundException("Confirmation not found.");
            }

            if (confirmation.getToken().equals(confirmEmailRequest.getToken())) {
                confirmationRepository.delete(confirmation);
                return new Response(true, "Email is successfully confirmed.");
            } else {
                throw new RuntimeException("Invalid token.");
            }
        } catch (ConfirmationNotFoundException e) {
            throw new ConfirmationNotFoundException("Confirmation not found.");
        } catch (Exception e) {
            throw new RuntimeException("Error confirming email: " + e.getMessage());
        }
    }


}
