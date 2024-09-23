package app.ecommerce.modules.manage.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import app.ecommerce.model.User;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "branches")
public class Branch {

    @Id
    private String id; // Usamos UUID como identificador

    @Column(nullable = false)
    private String name; // Nombre de la sucursal

    @Column(nullable = false)
    private String address; // Dirección de la sucursal

    private String phone; // Teléfono de la sucursal (opcional)

    private String email; // Correo electrónico de la sucursal (opcional)

    @Column(nullable = false)
    private boolean isSuspended; // Indica si la sucursal está suspendida

    @OneToMany(mappedBy = "branch")
    @JsonIgnore// Ignora la serialización de los roles para evitar la recursión infinita
    private List<User> users; // Relación con los usuarios en esta sucursal

    // PrePersist para generar el UUID automáticamente
    @PrePersist
    public void prePersist() {
        if (id == null || id.isEmpty()) {
            id = UUID.randomUUID().toString();
        }
    }

    @Override
    public String toString() {
        return "Branch [id=" + id + ", name=" + name + ", address=" + address + ", phone=" + phone 
                + ", email=" + email + ", isSuspended=" + isSuspended + "]";
    }
}