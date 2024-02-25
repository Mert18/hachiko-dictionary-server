package com.m2t.hachikodictionary.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Data
@Document("accounts")
public class Account implements UserDetails {

    @Id
    private String id;

    private String username;

    private String password;

    private String email;

    private Role role;

    private boolean confirmed;

    private Set<LearnedWord> learnedWords;

    public Account() {

    }
    public Account(String id, String username, String password, String email, Role role, boolean confirmed, Set<LearnedWord> learnedWords) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.confirmed = confirmed;
        this.learnedWords = learnedWords;
    }

    public Account(String username, String password, String email, Role role) {
        this(null, username, password, email, role, false, Collections.emptySet());
    }

    public Account(String id, String username, String password, String email, Role role, boolean confirmed) {
        this(id, username, password, email, role, confirmed, Collections.emptySet());
    }

    public Account(String id, String username, String password, String email, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}