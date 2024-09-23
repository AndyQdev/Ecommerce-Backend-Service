package app.ecommerce.modules.usuario.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import app.ecommerce.modules.usuario.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String>{
}
