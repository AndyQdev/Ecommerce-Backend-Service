package app.ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clientes", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class Cliente {

    @Id
    private String id; // Se generará automáticamente un UUID como id

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String email; // Email único para cada cliente

    @Column(nullable = false)
    private String password; // Contraseña del cliente

    @Column(nullable = false)
    private String telefono; // Teléfono del cliente

    @Column(nullable = false)
    private String direccion; // Dirección del cliente

    @Column(name = "is_active", nullable = false)
    private boolean isActive; // Campo para saber si el cliente está activo o no

    // PrePersist para generar el UUID automáticamente
    @PrePersist
    public void prePersist() {
        if (id == null || id.isEmpty()) {
            id = UUID.randomUUID().toString();
        }
    }
}