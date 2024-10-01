package app.ecommerce.modules.compras.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// import app.ecommerce.modules.compras.repositorys.CarritoRepository;
import app.ecommerce.modules.compras.repositorys.ItemCarritoRepository;
import app.ecommerce.modules.compras.services.CarritoService;
import app.ecommerce.modules.compras.model.CarritoDeCompras;
import app.ecommerce.modules.compras.model.ItemCarrito;
import app.ecommerce.modules.compras.model.ItemRequest;
@RestController
@RequestMapping("/api/carrito")
public class CarritoController {

    // @Autowired
    // private CarritoRepository carritoRepository;

    @Autowired
    private ItemCarritoRepository itemCarritoRepository;

    @Autowired
    private CarritoService carritoService;

    // Obtener carrito activo de un cliente
    @GetMapping("/{clienteId}")
    public ResponseEntity<CarritoDeCompras> getActiveCarritoByCliente(@PathVariable String clienteId) {
        Optional<CarritoDeCompras> carrito = carritoService.getActiveCarritoByCliente(clienteId);
        return carrito.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo carrito para un cliente (si no existe uno activo)
    @PostMapping("/crear/{clienteId}")
    public ResponseEntity<CarritoDeCompras> createCarrito(@PathVariable String clienteId) {
        CarritoDeCompras carrito = carritoService.createCarrito(clienteId);
        return ResponseEntity.ok(carrito);
    }

    // Agregar item al carrito
    @PostMapping("/agregar-item")
    public ResponseEntity<ItemCarrito> agregarItemAlCarrito(@RequestBody ItemRequest itemRequest) {
        ItemCarrito itemCarrito = carritoService.agregarItemAlCarrito(itemRequest);
        return ResponseEntity.ok(itemCarrito);
    }

    // Obtener todos los items del carrito
    @GetMapping("/{carritoId}/items")
    public List<ItemCarrito> getItemsDelCarrito(@PathVariable String carritoId) {
        return itemCarritoRepository.findByCarritoId(carritoId);
    }

    // Eliminar item del carrito
    @DeleteMapping("/eliminar-item/{itemId}")
    public ResponseEntity<Void> eliminarItemDelCarrito(@PathVariable String itemId) {
        carritoService.eliminarItemDelCarrito(itemId);
        return ResponseEntity.ok().build();
    }

    // Vaciar carrito
    @DeleteMapping("/vaciar/{carritoId}")
    public ResponseEntity<Void> vaciarCarrito(@PathVariable String carritoId) {
        carritoService.vaciarCarrito(carritoId);
        return ResponseEntity.ok().build();
    }
}
