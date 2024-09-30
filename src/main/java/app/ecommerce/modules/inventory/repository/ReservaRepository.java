package app.ecommerce.modules.inventory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.ecommerce.modules.inventory.model.Reserva;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, String>{
    // Método para buscar reservas por el ID de usuario
    List<Reserva> findByUserId(String userId);
}
