package com.m2t.hachikodictionary.repository;

import com.m2t.hachikodictionary.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {

    Account findAccountByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
