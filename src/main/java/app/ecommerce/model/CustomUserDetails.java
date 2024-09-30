package app.ecommerce.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private final Cliente cliente;

    public CustomUserDetails(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Podrías definir roles o permisos si lo necesitas
        return Collections.emptyList(); // Clientes no tienen roles por defecto
    }

    @Override
    public String getPassword() {
        return cliente.getPassword(); // Retornar la contraseña del cliente si es necesaria
    }

    @Override
    public String getUsername() {
        return cliente.getEmail(); // Retornar el email como nombre de usuario
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return cliente.isActive(); // Si el cliente tiene un campo activo
    }

    public Cliente getCliente() {
        return cliente;
    }
}

