package com.m2t.hachikodictionary.controller;

import com.m2t.hachikodictionary.dto.account.ConfirmEmailRequest;
import com.m2t.hachikodictionary.dto.Response;
import com.m2t.hachikodictionary.service.ConfirmationService;
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
        return ResponseEntity.ok(confirmationService.confirmEmail(confirmEmailRequest));
    }

}
