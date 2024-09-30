package app.ecommerce.modules.inventory.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservaRequest {
    String userId;
    String productoId; 
    String fechaReserva; 
    String estado;
}
