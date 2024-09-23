package app.ecommerce.modules.inventory.model;


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
@Table(name = "productos")
public class Producto {

    @Id
    private String id; // Usamos UUID como identificador

    @Column(nullable = false)
    private String imagenUrl; // URL de la imagen del producto

    @Column(nullable = false, unique = true)
    private String nombre; // Nombre del producto

    @Column(nullable = false)
    private String descripcion; // Descripción del producto

    @Column(nullable = false)
    private int stock; // Cantidad de stock disponible

    @Column(nullable = false)
    private float precio; // Precio del producto

    @Column(nullable = false)
    private String marca; // Marca del producto

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria; // Relación con la entidad Categoría

    // PrePersist para generar el UUID automáticamente
    @PrePersist
    public void prePersist() {
        if (id == null || id.isEmpty()) {
            id = UUID.randomUUID().toString();
        }
    }

    @Override
    public String toString() {
        return "Producto [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", precio=" + precio + "]";
    }
}
