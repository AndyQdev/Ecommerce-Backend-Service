package app.ecommerce.model;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import app.ecommerce.modules.manage.model.Branch;
import app.ecommerce.modules.usuario.model.Role;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="usuarios" , uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
public class User implements UserDetails{

    @Id
    private String id; // Se generará automáticamente un UUID como id

    private String nombre;

    @Column(nullable = false, unique = true)
    private String username;

    private String direccion;
    private String telefono;
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role; // Relación con el rol

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch; // Relación con la sucursal

    @Column(name = "is_active")
    private boolean isActive;

    // PrePersist para generar el UUID automáticamente
    @PrePersist
    public void prePersist() {
        if (id == null || id.isEmpty()) {
            id = UUID.randomUUID().toString();
        }
    }

    // Implementación de UserDetails

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getName())); // Retornar los roles
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
        return true; // Usar el campo `isActive` para definir si está habilitado o no
    }
}