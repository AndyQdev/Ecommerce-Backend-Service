package app.ecommerce.auth;


import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterClient{
    String email;
    String nombre;
    String password;
    String direccion;
    String telefono;
    boolean isActive;
}
