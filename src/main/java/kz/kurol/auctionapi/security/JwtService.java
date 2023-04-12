package kz.kurol.auctionapi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "2B4B6250655368566D5970337336763979244226452948404D635166546A576E";
    public String extractUsername(String jwtToken) {

        return extractClaim(jwtToken, Claims::getSubject); //subject is email client

    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims); // extract извлекать
    }

    public String generateToken(UserDetails clientDetails){
        return generateToken(new HashMap<>(),clientDetails);
    }

    public String generateToken(Map<String,Object> extraClaims, UserDetails clientDetails){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(clientDetails.getUsername()) // username -- email -- subject
                .setIssuedAt(new Date(System.currentTimeMillis())) // this will help to check expiration date is valid or not
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact(); // will generate and return a token
    }

    public boolean isTokenValid(String token, UserDetails clientDetails){
        final String username = extractUsername(token);
        return (username.equals(clientDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey()) // secret key
                .build()
                .parseClaimsJws(token)
                .getBody(); // get all claims from token
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY); //todo what is decoders?
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
