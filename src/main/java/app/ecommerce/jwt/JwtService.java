package app.ecommerce.jwt;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map; // Este es el Map correcto
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


@Service
public class JwtService {

    private static final String SECRET_KEY = "523ASDASFDSDF21SAD53SAD5AS43D5S3D5ASD512JKJOQONQIWUBQUBWEBU";

    public String getToken(UserDetails user) {
        return getToken(new HashMap<>(), user);
    }

    private String getToken(Map<String, Object> extraClaims, UserDetails user) {
        return Jwts
               .builder()
               .setClaims(extraClaims) // Puedes agregar claims adicionales aquí
               .setSubject(user.getUsername())
               .setIssuedAt(new Date(System.currentTimeMillis()))
               .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) // Expira en 24 minutos
               .signWith(getKey(), SignatureAlgorithm.HS256) // Firma con la clave secreta
               .compact();
    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Método para validar un token en general (por ejemplo, en el endpoint /auth/check-token)
    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token);
            return true; // Si no hay excepción, el token es válido
        } catch (Exception e) {
            return false; // El token no es válido o ha expirado
        }
    }

    // Método para extraer el nombre de usuario (subject) del token
    public String getUsernameFromTokenFront(String token) {
        return Jwts.parserBuilder()
                   .setSigningKey(getKey())
                   .build()
                   .parseClaimsJws(token)
                   .getBody()
                   .getSubject();
    }

    // Cambiar aquí: este método ahora utiliza parseClaimsJws para JWTs firmados
    private Claims getAllClaims(String token) {
        return Jwts
               .parserBuilder()
               .setSigningKey(getKey())
               .build()
               .parseClaimsJws(token) // Cambia a parseClaimsJws para tokens firmados
               .getBody();
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }
}