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
    val confirmed: Boolean = false,

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val learnedWords: Set<LearnedWord>
): UserDetails {

    constructor(id: String, username: String, password: String, email: String, role: Role) : this(
        id,
        username,
        password,
        email,
        role,
        false,
        emptySet()
    ) {

    }
    constructor(username: String, password: String, email: String, role: Role) : this(
        null,
        username,
        password,
        email,
        role,
        false,
        emptySet(),
    ) {

    }

    constructor(id: String, username: String, password: String, email: String, role: Role, confirmed: Boolean) : this(
        id,
        username,
        password,
        email,
        role,
        confirmed,
        emptySet()
    ) {

    }


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