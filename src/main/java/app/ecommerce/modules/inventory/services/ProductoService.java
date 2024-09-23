package app.ecommerce.modules.inventory.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.ecommerce.modules.inventory.model.Categoria;
import app.ecommerce.modules.inventory.model.Producto;
import app.ecommerce.modules.inventory.model.ProductoRequest;
import app.ecommerce.modules.inventory.repository.ProductoRepository;
import app.ecommerce.modules.inventory.repository.CategoryRepository;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoryRepository categoriaRepository;

    public Producto createProducto(ProductoRequest producto) {
        // Obtener el categoria_id del producto

        // Buscar la categoría en la base de datos
        Categoria categoria = categoriaRepository.findById(producto.getCategoria_id())
            .orElseThrow(() -> new IllegalArgumentException("Categoria not found with id: " + producto.getCategoria_id()));

        // Asignar la categoría al producto
        Producto product = Producto.builder()
        .imagenUrl(producto.getImagenUrl())
        .nombre(producto.getNombre())
        .descripcion(producto.getDescripcion())
        .stock(producto.getStock())
        .precio(producto.getPrecio())
        .marca(producto.getMarca())
        .categoria(categoria)
        .build();

        // Guardar el producto en la base de datos
        return productoRepository.save(product);
    }
}