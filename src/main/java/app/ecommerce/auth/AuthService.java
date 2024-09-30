package app.ecommerce.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import app.ecommerce.jwt.JwtService;
import app.ecommerce.model.Cliente;
import app.ecommerce.model.User;
import app.ecommerce.repository.ClienteRepository;
import app.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import app.ecommerce.modules.usuario.repository.RoleRepository;
import app.ecommerce.modules.manage.model.Branch;
import app.ecommerce.modules.manage.repository.BranchRepository;
import app.ecommerce.modules.usuario.model.Role;
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository; 
    private final BranchRepository branchRepository; 
    private final ClienteRepository clienteRepository; 
    

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.getToken(user);
        return AuthResponse.builder()
        .token(token)
        .build();
    }
    public AuthResponse loginCliente(LoginRequest request) {
        Cliente cliente = clienteRepository.findByEmail(request.getUsername()).orElseThrow();
        System.out.println(request);
        if (!passwordEncoder.matches(request.getPassword(), cliente.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }
        
        if (!cliente.getEmail().equals(request.getUsername())) {
            throw new IllegalArgumentException("Invalid email");
        }
        String token = jwtService.getTokenWithoutExpiration(cliente);
        return AuthResponse.builder()
        .token(token)
        .build();
    }

    public AuthResponse registerClient(RegisterClient request) {
        Cliente client = Cliente.builder()
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .direccion(request.getDireccion())
        .telefono(request.getTelefono())
        .nombre(request.getNombre())
        .isActive(request.isActive)
        .build();
        
        clienteRepository.save(client);
        return AuthResponse.builder()
        .token(jwtService.getTokenWithoutExpiration(client))
        .build();
    }
    public AuthResponse register(RegisterRequest request) {
        Role role = roleRepository.findById(request.getRoleId())
            .orElseThrow(() -> new IllegalArgumentException("Role not found"));
        Branch branch = branchRepository.findById(request.getBranchId())
            .orElseThrow(() -> new IllegalArgumentException("Role not found"));
        User user = User.builder()
        .username(request.getUsername())
        .password(passwordEncoder.encode(request.getPassword()))
        .direccion(request.getDireccion())
        .telefono(request.getTelefono())
        .role(role)
        .nombre(request.getNombre())
        .branch(branch)
        .isActive(request.is_active)
        .build();
        
        userRepository.save(user);
        return AuthResponse.builder()
        .token(jwtService.getToken(user))
        .build();
    }

}
