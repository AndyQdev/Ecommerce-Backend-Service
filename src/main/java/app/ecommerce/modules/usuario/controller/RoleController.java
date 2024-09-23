package app.ecommerce.modules.usuario.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import app.ecommerce.modules.usuario.model.Role;
import app.ecommerce.modules.usuario.model.RoleRequest;
import app.ecommerce.modules.usuario.repository.RoleRepository;
import app.ecommerce.modules.usuario.services.RoleServices;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleServices roleService;
    @GetMapping
    public List<Role> getAllRoles(){
        return roleRepository.findAll();
    }

    @GetMapping("/{id}")
    public Role getRoleById(@PathVariable String id) {
        return roleRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Role not found with id: " + id));
    }
    
    @PostMapping
    public Role createUser(@RequestBody RoleRequest role){
        return roleService.createRole(role);
    }
}
