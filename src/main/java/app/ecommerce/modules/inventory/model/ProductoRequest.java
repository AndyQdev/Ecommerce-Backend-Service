package app.ecommerce.modules.inventory.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductoRequest {
    String imagenUrl;
    String nombre; 
    String descripcion; 
    int stock;
    float precio; 
    String marca; 
    String categoria_id;
    String branch_id;
}
