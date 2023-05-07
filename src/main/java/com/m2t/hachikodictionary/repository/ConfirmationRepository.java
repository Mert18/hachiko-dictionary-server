package com.m2t.hachikodictionary.repository;

import com.m2t.hachikodictionary.model.Confirmation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmationRepository extends JpaRepository<Confirmation, String> {

    Confirmation findConfirmationByEmail(String email);

    Boolean existsByEmail(String email);
}
