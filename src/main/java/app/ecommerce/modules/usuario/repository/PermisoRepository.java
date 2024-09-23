package app.ecommerce.modules.usuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.ecommerce.modules.usuario.model.Permiso;

@Repository
public interface PermisoRepository extends JpaRepository<Permiso, String>{
}
