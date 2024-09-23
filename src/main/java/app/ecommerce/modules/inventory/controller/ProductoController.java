package app.ecommerce.modules.inventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        .orElseThrow(() -> new IllegalArgumentException("Role not found with id: " + id));
    }

    @PostMapping
    public Producto createProducto(@RequestBody ProductoRequest producto){

        return productoService.createProducto(producto);
    }
}
