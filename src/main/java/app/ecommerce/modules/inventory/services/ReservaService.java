package app.ecommerce.modules.inventory.services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.ecommerce.model.User;
import app.ecommerce.modules.inventory.model.Categoria;
import app.ecommerce.modules.inventory.model.Producto;
import app.ecommerce.modules.inventory.model.ProductoRequest;
import app.ecommerce.modules.inventory.model.Reserva;
import app.ecommerce.modules.inventory.model.ReservaRequest;
import app.ecommerce.modules.inventory.repository.ProductoRepository;
import app.ecommerce.modules.inventory.repository.ReservaRepository;
import app.ecommerce.modules.manage.model.Branch;
import app.ecommerce.modules.manage.repository.BranchRepository;
import app.ecommerce.repository.UserRepository;
import app.ecommerce.service.CrudService;
import app.ecommerce.modules.inventory.repository.CategoryRepository;


@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CrudService crudService;
    public Reserva createReserva(ReservaRequest reserva) {
        // Obtener el categoria_id del producto

        // Buscar la categoría en la base de datos
        User usuario = userRepository.findById(reserva.getUserId())
            .orElseThrow(() -> new IllegalArgumentException("Categoria not found with id: " + reserva.getUserId()));
        Producto producto = productoRepository.findById(reserva.getProductoId())
            .orElseThrow(() -> new IllegalArgumentException("Sucursal not found with id: " + reserva.getProductoId()));

        // Asignar la categoría al producto
        Reserva reserv = Reserva.builder()
        .fechaReserva(reserva.getFechaReserva())
        .user(usuario)
        .product(producto)
        .estado(reserva.getEstado())
        .build();

        // Guardar el producto en la base de datos
        return reservaRepository.save(reserv);
    }

    // Método para eliminar un producto y devolverlo
    public Optional<Reserva> deleteReserva(String id) {
        // Usamos el método genérico para eliminar el producto
        return crudService.deleteEntity(reservaRepository, id);
    }
}