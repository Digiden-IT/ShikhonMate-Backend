package dev.jahid.user_auth_db_config_boilerplate.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshExpiration;

    private SecretKey getSignKey() {
        return Keys.hmacShaKeyFor( Decoders.BASE64.decode( secretKey ) );
    }

    public String generateToken( CustomUserDetails customUserDetails ) {

        Date now = new Date();
        Date expiryDate = new Date( now.getTime() + jwtExpiration );

        Map<String, Object> claims = new HashMap<>();
        claims.put( "user", customUserDetails );

        return Jwts.builder()
                .claims( claims )
                .issuedAt(now)
                .expiration( expiryDate )
                .compressWith( Jwts.ZIP.GZIP )
                .signWith( getSignKey(), Jwts.SIG.HS512 )
                .compact();
    }

    public String generateRefreshToken( CustomUserDetails customUserDetails ) {

        Date now = new Date();
        Date expiryDate = new Date( now.getTime() + refreshExpiration );

        Map<String, Object> claims = new HashMap<>();
        claims.put( "user", customUserDetails );

        return Jwts.builder()
                .claims( claims )
                .issuedAt( now )
                .expiration( expiryDate )
                .signWith( getSignKey(), Jwts.SIG.HS256 )
                .compact();
    }

    public CustomUserDetails extractUser( String token ) {
        Claims claims = Jwts.parser()
                .verifyWith( getSignKey() )
                .build()
                .parseSignedClaims( token )
                .getPayload();

        return (CustomUserDetails) claims.get( "user" );
    }

    public boolean validateToken( String token ) {
        try {
            Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims( token );
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

