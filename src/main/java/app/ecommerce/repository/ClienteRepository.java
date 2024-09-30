package app.ecommerce.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.ecommerce.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String>{
    Optional<Cliente> findByEmail(String email);
}
