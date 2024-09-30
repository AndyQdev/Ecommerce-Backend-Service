package app.ecommerce.auth;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.ecommerce.jwt.JwtService;
import app.ecommerce.model.Cliente;
import app.ecommerce.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor

public class AuthController {
    
    private final AuthService authService;
    private final JwtService jwtService; // Servicio que maneja los tokens
    private final ClienteRepository clienteRepository; 

    @PostMapping(value = "/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }
    
    @PostMapping(value = "/login-cliente")
    public ResponseEntity<AuthResponse> loginCliente(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.loginCliente(request));
    }

    @PostMapping(value = "/register-cliente")
    public ResponseEntity<AuthResponse> registerCliente(@RequestBody RegisterClient request){
        return ResponseEntity.ok(authService.registerClient(request));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authService.register(request));
    }
    
    // Verificación de token
    @GetMapping("/check-token")
    public ResponseEntity<?> checkToken(@RequestHeader("Authorization") String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7); // Eliminar el prefijo 'Bearer '
        }

        // Verificar si el token es válido
        if (jwtService.isTokenValid(token)) {
            String username = jwtService.getUsernameFromToken(token); // Utiliza getUsernameFromToken
            return ResponseEntity.ok(Map.of("username", username)); // Retorna la información del usuario
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido o expirado");
        }
    }

    // Verificación de token para clientes
    @GetMapping("/check-token-cliente")
    public ResponseEntity<?> checkTokenCliente(@RequestHeader("Authorization") String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7); // Eliminar el prefijo 'Bearer '
        }

        // Verificar si el token es válido
        if (jwtService.isTokenValid(token)) {
            String email = jwtService.getUsernameFromToken(token); // Extrae el email en lugar de username
            return ResponseEntity.ok(Map.of("email", email)); // Retorna la información del cliente
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido o expirado");
        }
    }

    @GetMapping("/check-existe-cliente")
    public ResponseEntity<?> checkUserExists(@RequestParam("email") String email) {
        System.out.println(email);
        Optional<Cliente> existingClient = clienteRepository.findByEmail(email);
        if (existingClient.isPresent()) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }
    }


}
