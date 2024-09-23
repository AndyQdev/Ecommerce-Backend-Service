package app.ecommerce.modules.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.ecommerce.modules.inventory.model.Categoria;

@Repository
public interface CategoryRepository extends JpaRepository<Categoria, String>{
}
