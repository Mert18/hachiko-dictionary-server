package com.m2t.hachikodictionary.repository;

import com.m2t.hachikodictionary.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account, String> {
    Account findAccountByUsername(String username);

    Account findAccountByEmail(String email);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
