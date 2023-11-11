package com.m2t.hachikodictionary.controller;

import com.m2t.hachikodictionary.dto.ConfirmEmailRequest;
import com.m2t.hachikodictionary.dto.Response;
import com.m2t.hachikodictionary.exception.ConfirmationNotFoundException;
import com.m2t.hachikodictionary.exception.InvalidTokenException;
import com.m2t.hachikodictionary.service.ConfirmationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/confirmation")
public class ConfirmationController {

    private final ConfirmationService confirmationService;

    public ConfirmationController(ConfirmationService confirmationService) {
        this.confirmationService = confirmationService;
    }

    @PostMapping("/confirm")
    public ResponseEntity<Response> confirmEmail(@RequestBody ConfirmEmailRequest confirmEmailRequest) {
        try {
            return ResponseEntity
                    .ok(confirmationService.confirmEmail(confirmEmailRequest));
        } catch (ConfirmationNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new Response(false, e.getMessage()));

        } catch (InvalidTokenException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new Response(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response(false, "Confirmation failed."));
        }
    }

}
