package app.ecommerce.modules.manage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.ecommerce.modules.manage.model.Branch;

@Repository
public interface BranchRepository extends JpaRepository<Branch, String>{
}
