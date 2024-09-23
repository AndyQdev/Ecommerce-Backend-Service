package app.ecommerce.modules.inventory.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categorias")
public class Categoria {

    @Id
    private String id; // Usamos UUID como identificador

    @Column(nullable = false, unique = true)
    private String nombre; // Nombre único de la categoría

    @Column(nullable = true)
    private String descripcion; // Descripción opcional de la categoría

    @OneToMany(mappedBy = "categoria")
    @JsonIgnore// Ignora la serialización de los roles para evitar la recursión infinita
    private List<Producto> productos; // Relación con la entidad Producto

    // PrePersist para generar el UUID automáticamente
    @PrePersist
    public void prePersist() {
        if (id == null || id.isEmpty()) {
            id = UUID.randomUUID().toString();
        }
    }

    @Override
    public String toString() {
        return "Categoria [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + "]";
    }
}