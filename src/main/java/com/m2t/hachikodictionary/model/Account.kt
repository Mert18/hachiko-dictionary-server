package com.m2t.hachikodictionary.model

import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "accounts")
data class Account (
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator") // Generate UUID
    val id: String?,

    // Defined as private, thus the default getter and setters are not used.
    // Because we wanted to override these methods from UserDetails.
    @Column(unique = true)
    private val username: String,
    private val password: String,
    @Column(unique = true)
    val email: String,

    @Enumerated(EnumType.STRING)
    val role: Role = Role.USER,

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "account_id")
    val learnedWords: Set<LearnedWord>
): UserDetails {
    constructor() : this(null, "", "", "", Role.USER, setOf())
    constructor(id: String, username: String, password: String, email: String): this (
        id,
        username,
        password,
        email,
        Role.USER,
        setOf()
    )

    constructor(username: String, password: String, role: Role) : this(
        null,
        username,
        password,
        "",
        role,
        setOf()
    )

    constructor(username: String, password: String, email: String, role: Role) : this(
        null,
        username,
        password,
        email,
        role,
        setOf()
    )

    constructor(username: String, password: String, email: String) : this(
        null,
        username,
        password,
        email,
        Role.USER,
        setOf()
    )

    constructor(id: String, username: String, password: String, email: String, role: Role) : this(
        id,
        username,
        password,
        email,
        role,
        setOf()
    )

    constructor(username: String) : this(
        null,
        username,
        "",
        "",
        Role.USER,
        setOf()
    )

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority(role.name))
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}