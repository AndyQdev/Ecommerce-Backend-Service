
package app.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
// import org.springframework.beans.BeanUtils;

import app.ecommerce.auth.RegisterRequest;
import app.ecommerce.model.User;
import app.ecommerce.modules.manage.model.Branch;
import app.ecommerce.modules.manage.repository.BranchRepository;
import app.ecommerce.modules.usuario.model.Role;
import app.ecommerce.modules.usuario.repository.RoleRepository;
import app.ecommerce.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository; 
    
    @Autowired
    private BranchRepository branchRepository; 

    // public Optional<User> patchUser(String id, RegisterRequest updatedUserDetails) {
    //     return userRepository.findById(id).map(existingUser -> {
    //         BeanUtils.copyProperties(updatedUserDetails, existingUser, getNullPropertyNames(updatedUserDetails));
    //         return userRepository.save(existingUser);
    //     });
    // }

    // // Método que devuelve los nombres de las propiedades nulas
    // private String[] getNullPropertyNames(RegisterRequest source) {
    //     return java.util.stream.Stream.of(source.getClass().getDeclaredFields())
    //         .filter(field -> {
    //             field.setAccessible(true);
    //             try {
    //                 return field.get(source) == null;
    //             } catch (IllegalAccessException e) {
    //                 return false;
    //             }
    //         })
    //         .map(field -> field.getName())
    //         .toArray(String[]::new);
    // }
    public Optional<User> patchUser(String id, RegisterRequest updatedUserDetails) {
        
        Optional<User> userExist = userRepository.findById(id);
        
        if (userExist.isPresent()) {
            User user = userExist.get();
            Role role = roleRepository.findById(user.getRole().getId())
            .orElseThrow(() -> new IllegalArgumentException("Role not found"));
            Branch branch = branchRepository.findById(user.getBranch().getId())
            .orElseThrow(() -> new IllegalArgumentException("Role not found"));
            if (updatedUserDetails.getUsername() != null) {
                user.setUsername(updatedUserDetails.getUsername());
            }
            if (updatedUserDetails.getPassword() != null) {
                user.setPassword(updatedUserDetails.getPassword());
            }
            if (updatedUserDetails.getDireccion() != null) {
                user.setDireccion(updatedUserDetails.getDireccion());
            }
            if (updatedUserDetails.getTelefono() != null) {
                user.setTelefono(updatedUserDetails.getTelefono());
            }
            if (updatedUserDetails.getNombre() != null) {
                user.setNombre(updatedUserDetails.getNombre());
            }
            if (updatedUserDetails.getRoleId() != null) {
                user.setRole(role);
            }
            if (updatedUserDetails.getBranchId() != null) {
                user.setBranch(branch);
            }
            // Guardar cambios en la base de datos
            userRepository.save(user);

            return Optional.of(user);
        }
        
        return Optional.empty();
    }
}