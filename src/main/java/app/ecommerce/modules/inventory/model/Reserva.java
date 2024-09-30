package app.ecommerce.modules.inventory.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import app.ecommerce.model.User;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    private String id; // Usamos UUID como identificador

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Relación con el usuario que realiza la reserva

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto product; // Relación con el producto reservado

    @Column(nullable = false)
    private String fechaReserva; // Fecha en que se realizó la reserva

    @Column(nullable = false)
    private String estado; // Estado de la reserva, por ejemplo: "Pendiente", "Completada", "Cancelada"

    @PrePersist
    public void prePersist() {
        if (id == null || id.isEmpty()) {
            id = UUID.randomUUID().toString();
        }
    }

    @Override
    public String toString() {
        return "Reserva [id=" + id + ", user=" + user + ", product=" + product + ", fechaReserva=" + fechaReserva + ", estado=" + estado + "]";
    }
}
