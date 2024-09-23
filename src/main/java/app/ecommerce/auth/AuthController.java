package app.ecommerce.auth;

import java.util.Map; 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.ecommerce.jwt.JwtService;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor

public class AuthController {
    
    private final AuthService authService;
    private final JwtService jwtService; // Servicio que maneja los tokens

    @PostMapping(value = "/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }
    
    @PostMapping(value = "/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authService.register(request));
    }
    
    @GetMapping("/check-token")
    public ResponseEntity<?> checkToken(@RequestHeader("Authorization") String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7); // Eliminar el prefijo 'Bearer '
        }

        // Verificar si el token es válido
        if (jwtService.isTokenValid(token)) {
            String username = jwtService.getUsernameFromTokenFront(token); // Extraer el usuario del token
            return ResponseEntity.ok(Map.of("username", username)); // Retorna la información del usuario
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido o expirado");
        }
    }
}
