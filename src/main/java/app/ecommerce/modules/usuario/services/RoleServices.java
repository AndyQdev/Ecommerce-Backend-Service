package app.ecommerce.modules.usuario.services;

import java.util.ArrayList;
import java.util.UUID;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.ecommerce.modules.usuario.model.Permiso;
import app.ecommerce.modules.usuario.model.Role;
import app.ecommerce.modules.usuario.model.RoleRequest;
import app.ecommerce.modules.usuario.repository.PermisoRepository;
import app.ecommerce.modules.usuario.repository.RoleRepository;

@Service
public class RoleServices {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermisoRepository permisoRepository;

    public Role createRole(RoleRequest roleRequest) {
        // 1. Buscar los permisos por los IDs proporcionados
        List<Permiso> permisos = new ArrayList<>();
        for (String permisoId : roleRequest.getPermissionsId()) {
            permisoRepository.findById(permisoId).ifPresent(permisos::add);
        }
        System.out.println(permisos);
        // 2. Crear un nuevo Role
        Role role = Role.builder()
                        .id(UUID.randomUUID().toString()) // Generar un UUID para el Role
                        .name(roleRequest.getName())
                        .permissions(permisos)
                        .build();

        // 3. Guardar el Role en la base de datos
        return roleRepository.save(role);
    }
}
