package app.ecommerce.modules.inventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

import app.ecommerce.modules.inventory.model.Producto;
import app.ecommerce.modules.inventory.model.ProductoRequest;
import app.ecommerce.modules.inventory.repository.ProductoRepository;
import app.ecommerce.modules.inventory.services.ProductoService;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private ProductoService productoService;
    @GetMapping
    public List<Producto> getAllProducto(){
        return productoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Producto getProductoById(@PathVariable String id) {
        return productoRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Producto not found with id: " + id));
    }

    
    @PostMapping
    public Producto createProducto(@RequestBody ProductoRequest producto){

        return productoService.createProducto(producto);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Producto> patchProducto(@PathVariable String id, @RequestBody ProductoRequest productoRequest) {
        return productoService.patchProduct(id, productoRequest)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Método para eliminar un producto y devolverlo
    @DeleteMapping("/{id}")
    public ResponseEntity<Producto> deleteProducto(@PathVariable String id) {
        Optional<Producto> deletedProducto = productoService.deleteProducto(id);
        
        // Si el producto fue encontrado y eliminado, devolverlo con 200 OK
        return deletedProducto.map(ResponseEntity::ok)
                              // Si no se encuentra, devolver un 404 Not Found
                              .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
