package app.ecommerce.modules.compras.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import app.ecommerce.model.Cliente;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "carrito_compras")
public class CarritoDeCompras {

    @Id
    private String id; // UUID generado automáticamente

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente; // Relación con el cliente

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore// Ignora la serialización de los roles para evitar la recursión infinita
    private List<ItemCarrito> items; // Lista de items en el carrito

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion; // Fecha de creación del carrito

    @Column(name = "estado", nullable = false)
    private String estado; // Estado del carrito (ej. "pendiente", "completado")

    @Column(name = "is_active", nullable = false)
    private boolean isActive; // Indicador de si el carrito está activo

    @PrePersist
    public void prePersist() {
        if (id == null || id.isEmpty()) {
            id = UUID.randomUUID().toString();
        }
        if (fechaCreacion == null) {
            fechaCreacion = LocalDateTime.now();
        }
        if (estado == null) {
            estado = "pendiente";
        }
    }
}
