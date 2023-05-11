package com.m2t.hachikodictionary.config;

import com.m2t.hachikodictionary.dto.AuthenticationResponse;
import com.m2t.hachikodictionary.model.Account;
import com.m2t.hachikodictionary.service.AccountService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {

    private final AccountService accountService;
    private String jwtSecret;
    private int refreshExpirationDateInMs;
    private int accessExpirationDateInMs;
    private final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    public JWTService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Value("${jwt.secret}")
    public void setJwtSecret(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    @Value("${jwt.refreshExpirationDateInMs}")
    public void setRefreshExpirationDateInMs(int refreshExpirationDateInMs) {
        this.refreshExpirationDateInMs = refreshExpirationDateInMs;
    }

    @Value("${jwt.accessExpirationDateInMs}")
    public void setAccessExpirationDateInMs(int accessExpirationDateInMs) {
        this.accessExpirationDateInMs = accessExpirationDateInMs;
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractAccountId(String token) {
        return extractClaim(token, claims -> claims.get("accountId", String.class));
    }

    // Generic method for extracting one claim from the token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public AuthenticationResponse generateToken(Account account) {
        Map<String, Object> extraClaims = new HashMap<>();
        Collection<? extends GrantedAuthority> roles = account.getAuthorities();
        if(roles.contains(new SimpleGrantedAuthority("ADMIN"))) {
            extraClaims.put("isAdmin", true);
        }
        if(roles.contains(new SimpleGrantedAuthority("USER"))) {
            extraClaims.put("isUser", true);
        }
        extraClaims.put("accountId", account.getId());
        extraClaims.put("email", account.getEmail());
        String accessToken = generateAccessToken(extraClaims, account);
        String refreshToken = generateRefreshToken(extraClaims, account);
        Claims accessTokenClaims = extractAllClaims(accessToken);
        Claims refreshTokenClaims = extractAllClaims(refreshToken);

        LocalDateTime accessTokenExpiresAt = accessTokenClaims.getExpiration().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime refreshTokenExpiresAt = refreshTokenClaims.getExpiration().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        AuthenticationResponse authenticationResponse = new AuthenticationResponse(
                        accessToken,
                        refreshToken,
                "Bearer",
                        accessTokenExpiresAt,
                        refreshTokenExpiresAt,
                        roles.contains(new SimpleGrantedAuthority("USER")),
                        roles.contains(new SimpleGrantedAuthority("ADMIN")),
                        account.getUsername(),
                        account.getEmail(),
                        account.getRole()
                );

        return authenticationResponse;
    }

    // Generate token for a user and configuration.
    public String generateAccessToken(Map<String, Object> extraClaims, Account account) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(account.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + accessExpirationDateInMs)) // 1 hour
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(Map<String, Object> extraClaims, Account account) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(account.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpirationDateInMs))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    public boolean isTokenValid(String token, Account account) {
        final String username = extractUsername(token);
        boolean isTokenValid = username.equals(account.getUsername()) && !isTokenExpired(token);
        return isTokenValid;
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
