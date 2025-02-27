package dev.jahid.user_auth_db_config_boilerplate.security;

import dev.jahid.user_auth_db_config_boilerplate.security.service.CustomUserDetailsService;
import dev.jahid.user_auth_db_config_boilerplate.user.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor( onConstructor_ = {@Autowired} )
public class JwtUtil {

    private final CustomUserDetailsService userDetailsService;

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshExpiration;

    private SecretKey getSignKey() {
        return Keys.hmacShaKeyFor( Decoders.BASE64.decode( secretKey ) );
    }

    public String generateToken( CustomUserDetails customUserDetails, Long expirationTimeInMilliseconds ) {

        Date now = new Date();
        Date expiryDate = new Date( now.getTime() + expirationTimeInMilliseconds );

        Map<String, Object> claims = new HashMap<>();
        claims.put( "email", customUserDetails.getEmail() );

        return Jwts.builder()
                .claims( claims )
                .issuedAt( now )
                .expiration( expiryDate )
                .compressWith( Jwts.ZIP.GZIP )
                .signWith( getSignKey(), Jwts.SIG.HS512 )
                .compact();
    }

    public String generateAccessToken( CustomUserDetails customUserDetails ) {
        return generateToken( customUserDetails, jwtExpiration );
    }

    public String generateRefreshToken( CustomUserDetails customUserDetails ) {

        return generateToken( customUserDetails, refreshExpiration );
    }

    public UserDetails extractUser(String token ) {
        Claims claims = Jwts.parser()
                .verifyWith( getSignKey() )
                .build()
                .parseSignedClaims( token )
                .getPayload();

        String email = (String) claims.get( "email" );
        return userDetailsService.loadUserByUsername( email ) ;
    }

    public boolean validateToken( String token ) {
        try {
            Jwts.parser().verifyWith( getSignKey() ).build().parseSignedClaims( token );
            return true;
        } catch ( ExpiredJwtException e ) {
            System.out.println( "JWT expired at: " + e.getClaims().getExpiration() );
            return false;
        } catch ( JwtException | IllegalArgumentException e ) {
            System.out.println( "Invalid JWT: " + e.getMessage() );
            return false;
        }
    }
}

