package com.m2t.hachikodictionary.repository;

import com.m2t.hachikodictionary.model.Account;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account, String> {
    Account findAccountByUsername(String username);

    Account findAccountByEmail(String email);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
