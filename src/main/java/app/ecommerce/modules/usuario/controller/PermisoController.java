package app.ecommerce.modules.usuario.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.ecommerce.modules.usuario.model.Permiso;
import app.ecommerce.modules.usuario.repository.PermisoRepository;

@RestController
@RequestMapping("/api/permiso")
public class PermisoController {
        @Autowired
    private PermisoRepository permisoRepository;

    @GetMapping
    public List<Permiso> getAllRoles(){
        return permisoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Permiso getAllRoleById(@RequestBody String id){
        return permisoRepository.findById(id).get();
    }
    
    @PostMapping
    public Permiso createUser(@RequestBody Permiso permiso){
        return permisoRepository.save(permiso);
    }
}
