package app.ecommerce.modules.inventory.services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.ecommerce.modules.inventory.model.Categoria;
import app.ecommerce.modules.inventory.model.Producto;
import app.ecommerce.modules.inventory.model.ProductoRequest;
import app.ecommerce.modules.inventory.repository.ProductoRepository;
import app.ecommerce.modules.manage.model.Branch;
import app.ecommerce.modules.manage.repository.BranchRepository;
import app.ecommerce.service.CrudService;
import app.ecommerce.modules.inventory.repository.CategoryRepository;


@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoryRepository categoriaRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private CrudService crudService;
    public Producto createProducto(ProductoRequest producto) {
        // Obtener el categoria_id del producto

        // Buscar la categoría en la base de datos
        Categoria categoria = categoriaRepository.findById(producto.getCategoria_id())
            .orElseThrow(() -> new IllegalArgumentException("Categoria not found with id: " + producto.getCategoria_id()));
        Branch branch = branchRepository.findById(producto.getBranch_id())
            .orElseThrow(() -> new IllegalArgumentException("Sucursal not found with id: " + producto.getBranch_id()));

        // Asignar la categoría al producto
        Producto product = Producto.builder()
        .imagenUrl(producto.getImagenUrl())
        .nombre(producto.getNombre())
        .descripcion(producto.getDescripcion())
        .stock(producto.getStock())
        .precio(producto.getPrecio())
        .marca(producto.getMarca())
        .categoria(categoria)
        .branch(branch)
        .build();

        // Guardar el producto en la base de datos
        return productoRepository.save(product);
    }

    public Optional<Producto> patchProduct(String id, ProductoRequest updatedProductDetails) {
        return crudService.patchEntity(
            productoRepository, 
            id, 
            updatedProductDetails, 
            request -> {
                Producto producto = productoRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Product not found"));
                
                // Cargar relaciones de Branch y Categoria
                if (request.getCategoria_id() != null) {
                    Categoria categoria = categoriaRepository.findById(request.getCategoria_id())
                            .orElseThrow(() -> new IllegalArgumentException("Category not found"));
                    producto.setCategoria(categoria);
                }

                if (request.getBranch_id() != null) {
                    Branch branch = branchRepository.findById(request.getBranch_id())
                            .orElseThrow(() -> new IllegalArgumentException("Branch not found"));
                    producto.setBranch(branch);
                }

                return producto; // Devolvemos el producto actualizado
            }
        );
    }

    // Método para eliminar un producto y devolverlo
    public Optional<Producto> deleteProducto(String id) {
        // Usamos el método genérico para eliminar el producto
        return crudService.deleteEntity(productoRepository, id);
    }

    // public Producto patchUser(String id, ProductoRequest producto) {
        
    //     Optional<Producto> productoExist = productoRepository.findById(id);
        
    //     if (productoExist.isPresent()) {
    //         Producto user = productoExist.get();
    //         Role role = roleRepository.findById(updatedUserDetails.getRoleId())
    //         .orElseThrow(() -> new IllegalArgumentException("Role not found"));
    //         Branch branch = branchRepository.findById(updatedUserDetails.getBranchId())
    //         .orElseThrow(() -> new IllegalArgumentException("Role not found"));
    //         if (updatedUserDetails.getUsername() != null) {
    //             user.setUsername(updatedUserDetails.getUsername());
    //         }
    //         if (updatedUserDetails.getPassword() != null) {
    //             user.setPassword(passwordEncoder.encode(updatedUserDetails.getPassword()));
    //         }
    //         if (updatedUserDetails.getDireccion() != null) {
    //             user.setDireccion(updatedUserDetails.getDireccion());
    //         }
    //         if (updatedUserDetails.getTelefono() != null) {
    //             user.setTelefono(updatedUserDetails.getTelefono());
    //         }
    //         if (updatedUserDetails.getNombre() != null) {
    //             user.setNombre(updatedUserDetails.getNombre());
    //         }
    //         if (updatedUserDetails.getRoleId() != null) {
    //             user.setRole(role);
    //         }
    //         if (updatedUserDetails.getBranchId() != null) {
    //             user.setBranch(branch);
    //         }
    //         // Guardar cambios en la base de datos
    //         userRepository.save(user);

    //         return Optional.of(user);
    //     }
        
    //     return Optional.empty();
    // }
}