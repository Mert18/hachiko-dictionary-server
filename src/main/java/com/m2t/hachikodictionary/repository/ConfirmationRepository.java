package com.m2t.hachikodictionary.repository;

import com.m2t.hachikodictionary.model.Confirmation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfirmationRepository extends MongoRepository<Confirmation, String> {

    Confirmation findConfirmationByEmail(String email);

    Boolean existsByEmail(String email);
}
