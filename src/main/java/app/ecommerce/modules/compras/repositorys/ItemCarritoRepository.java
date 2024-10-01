package app.ecommerce.modules.compras.repositorys;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import app.ecommerce.modules.compras.model.ItemCarrito;

@Repository
public interface ItemCarritoRepository extends JpaRepository<ItemCarrito, String> {
    // Método para encontrar todos los items de un carrito por su ID
    List<ItemCarrito> findByCarritoId(String carritoId);
    // Método para eliminar todos los items de un carrito por su ID
    void deleteByCarritoId(String carritoId);
}