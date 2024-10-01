package app.ecommerce.modules.compras.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.ecommerce.modules.compras.model.CarritoDeCompras;

import java.util.Optional;

@Repository
public interface CarritoRepository extends JpaRepository<CarritoDeCompras, String> {
    Optional<CarritoDeCompras> findByClienteIdAndIsActiveIsTrue(String clienteId);
}