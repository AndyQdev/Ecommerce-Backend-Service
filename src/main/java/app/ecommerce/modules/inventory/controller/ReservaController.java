package app.ecommerce.modules.inventory.controller;

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

import app.ecommerce.modules.inventory.model.Reserva;
import app.ecommerce.modules.inventory.model.ReservaRequest;
import app.ecommerce.modules.inventory.services.ReservaService;
import app.ecommerce.modules.inventory.repository.ReservaRepository;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {
    @Autowired
    private ReservaRepository reservaRepository;
    @Autowired
    private ReservaService reservaService;
    @GetMapping
    public List<Reserva> getAllReserva(){
        return reservaRepository.findAll();
    }

    @GetMapping("/{id}")
    public Reserva getReservaById(@PathVariable String id) {
        return reservaRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Producto not found with id: " + id));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Reserva>> getReservasByClienteId(@PathVariable String clienteId) {
        List<Reserva> reservas = reservaRepository.findByClienteId(clienteId);
        
        if (reservas.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(reservas);
    }
    @PostMapping
    public Reserva createReserva(@RequestBody ReservaRequest reserva){

        return reservaService.createReserva(reserva);
    }
    // @PatchMapping("/{id}")
    // public ResponseEntity<Reserva> patchReserva(@PathVariable String id, @RequestBody ProductoRequest reservaRequest) {
    //     return reservaService.patchReserva(id, reservaRequest)
    //         .map(ResponseEntity::ok)
    //         .orElseGet(() -> ResponseEntity.notFound().build());
    // }

    // Método para eliminar un producto y devolverlo
    @DeleteMapping("/{id}")
    public ResponseEntity<Reserva> deleteReserva(@PathVariable String id) {
        Optional<Reserva> deletedReserva = reservaService.deleteReserva(id);
        
        // Si el producto fue encontrado y eliminado, devolverlo con 200 OK
        return deletedReserva.map(ResponseEntity::ok)
                              // Si no se encuentra, devolver un 404 Not Found
                              .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
