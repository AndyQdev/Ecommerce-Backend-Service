package app.ecommerce.modules.compras.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.ecommerce.model.Cliente;
import app.ecommerce.repository.ClienteRepository;
import app.ecommerce.modules.compras.repositorys.CarritoRepository;
import app.ecommerce.modules.compras.repositorys.ItemCarritoRepository;
import app.ecommerce.modules.inventory.model.Producto;
import app.ecommerce.modules.inventory.repository.ProductoRepository;
import app.ecommerce.modules.compras.model.CarritoDeCompras;
import app.ecommerce.modules.compras.model.ItemCarrito;
import app.ecommerce.modules.compras.model.ItemRequest;

import java.util.List;

@Service
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ItemCarritoRepository itemCarritoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProductoRepository productoRepository;

    // Obtener el carrito activo de un cliente
    public Optional<CarritoDeCompras> getActiveCarritoByCliente(String clienteId) {
        return carritoRepository.findByClienteIdAndIsActiveIsTrue(clienteId);
    }

    // Crear un nuevo carrito para un cliente (si no tiene uno activo)
    public CarritoDeCompras createCarrito(String clienteId) {
        // Verificar si el cliente tiene un carrito activo
        Optional<CarritoDeCompras> carritoExistente = carritoRepository.findByClienteIdAndIsActiveIsTrue(clienteId);
        if (carritoExistente.isPresent()) {
            throw new IllegalStateException("El cliente ya tiene un carrito activo.");
        }
    
        // Buscar el cliente por ID
        Cliente cliente = clienteRepository.findById(clienteId)
            .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + clienteId));
    
        // Crear un nuevo carrito
        CarritoDeCompras nuevoCarrito = CarritoDeCompras.builder()
            .cliente(cliente)
            .estado("pendiente") 
            .isActive(true)// Estado inicial del carrito
            .build();
    
        // Guardar el carrito en la base de datos
        return carritoRepository.save(nuevoCarrito);
    }

    // Agregar un item al carrito
    public ItemCarrito agregarItemAlCarrito(ItemRequest itemRequest) {
        // Obtener el carrito activo del cliente
        CarritoDeCompras carrito = carritoRepository.findById(itemRequest.getCarritoId())
            .orElseThrow(() -> new IllegalArgumentException("Carrito no encontrado con ID: " + itemRequest.getCarritoId()));

        // Buscar el producto
        Producto producto = productoRepository.findById(itemRequest.getProductoId())
            .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + itemRequest.getProductoId()));

        // Crear un nuevo itemCarrito
        ItemCarrito itemCarrito = ItemCarrito.builder()
            .carrito(carrito)
            .producto(producto)
            .cantidad(itemRequest.getCantidad())
            .precioUnitario(itemRequest.getPrecioUnitario())
            .build();

        // Guardar el item en la base de datos
        itemCarritoRepository.save(itemCarrito);

        // Agregar el item a la lista del carrito
        List<ItemCarrito> items = carrito.getItems();
        items.add(itemCarrito);
        carrito.setItems(items);

        // Guardar el carrito actualizado
        carritoRepository.save(carrito);

        return itemCarrito;
    }

    // Eliminar un item del carrito
    public void eliminarItemDelCarrito(String itemId) {
        itemCarritoRepository.deleteById(itemId);
    }

    // Vaciar el carrito (elimina todos los items)
    public void vaciarCarrito(String carritoId) {
        itemCarritoRepository.deleteByCarritoId(carritoId);
    }
}
