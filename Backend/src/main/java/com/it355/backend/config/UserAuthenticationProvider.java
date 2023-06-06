package com.it355.backend.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.it355.backend.dto.CredentialsDTO;
import com.it355.backend.exception.impl.NoElementException;
import com.it355.backend.service.impl.AuthenticationServiceImpl;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.it355.backend.dto.LoginDTO;

import java.util.Base64;
import java.util.Collections;
import java.util.Date;

@Component
public class UserAuthenticationProvider {

    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration}")
    private int jwtExpiration;

    private final AuthenticationServiceImpl authenticationServiceImpl;

    public UserAuthenticationProvider(AuthenticationServiceImpl authenticationServiceImpl) {
        this.authenticationServiceImpl = authenticationServiceImpl;
    }

    @PostConstruct
    protected void init() {
        // this is to avoid having the raw secret key available in the JVM
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(LoginDTO user) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtExpiration); // 1 hour

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create()
                .withIssuer("jwtService")
                .withSubject(String.valueOf(user.getId()))
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .sign(algorithm);
    }


    // find user once logged in
    public Authentication validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        JWTVerifier verifier = JWT.require(algorithm)
                .build();

        DecodedJWT decoded = verifier.verify(token);

         LoginDTO user  = authenticationServiceImpl.findById(decoded.getSubject());

        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }

    // login to user
    public Authentication validateCredentials(CredentialsDTO credentialsDto) {
        LoginDTO user = authenticationServiceImpl.authenticate(credentialsDto);
        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }


}
