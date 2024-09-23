package app.ecommerce.modules.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.ecommerce.modules.inventory.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, String>{
}
