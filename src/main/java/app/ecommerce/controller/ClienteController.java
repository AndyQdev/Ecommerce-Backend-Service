package app.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.ecommerce.model.Cliente;
import app.ecommerce.repository.ClienteRepository;

@RestController

@RequestMapping("/api/clientes")
public class ClienteController {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    @GetMapping
    public List<Cliente> getAllClientes(){
        return clienteRepository.findAll();
    }

    @GetMapping("/{id}")
    public Cliente getClienteById(@PathVariable String id){
        return clienteRepository.findById(id).get();
    }
    
    @PostMapping
    public Cliente createCliente(@RequestBody Cliente user){
        return clienteRepository.save(user);
    }

    // @PatchMapping("/{id}")
    // public ResponseEntity<User> patchUser(@PathVariable String id, @RequestBody RegisterRequest user) {
    //     Optional<User> updatedUser = userService.patchUser(id, user);
    //     return updatedUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    // }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Cliente> deleteCliente(@PathVariable String id) {
        return clienteRepository.findById(id).map(user -> {
            clienteRepository.delete(user); // Elimina el usuario
            return ResponseEntity.ok(user); // Retorna el usuario eliminado
        }).orElseGet(() -> ResponseEntity.notFound().build()); // Retorna 404 si no se encuentra el usuario
    }
}
