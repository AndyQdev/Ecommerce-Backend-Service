package app.ecommerce.modules.compras.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequest {
    String carritoId;
    String productoId; 
    int cantidad; 
    float precioUnitario;
}