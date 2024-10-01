package app.ecommerce.modules.compras.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import app.ecommerce.modules.inventory.model.Producto;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "item_carrito")
public class ItemCarrito {

    @Id
    private String id; // UUID generado automáticamente

    @ManyToOne
    @JoinColumn(name = "carrito_id", nullable = false)
    private CarritoDeCompras carrito; // Relación con el carrito de compras

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto; // Relación con el producto

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad; // Cantidad de productos en el carrito

    @Column(name = "precio_unitario", nullable = false)
    private float precioUnitario; // Precio por unidad

    @PrePersist
    public void prePersist() {
        if (id == null || id.isEmpty()) {
            id = UUID.randomUUID().toString();
        }
    }
}
