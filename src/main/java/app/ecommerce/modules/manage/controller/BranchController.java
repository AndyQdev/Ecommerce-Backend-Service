package app.ecommerce.modules.manage.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.ecommerce.modules.manage.model.Branch;
import app.ecommerce.modules.manage.repository.BranchRepository;

@RestController
@RequestMapping("/api/branch")
public class BranchController {
    @Autowired
    private BranchRepository branchRepository;

    @GetMapping
    public List<Branch> getAllBranch(){
        return branchRepository.findAll();
    }

    @GetMapping("/{id}")
    public Branch getBranchById(@PathVariable String id) {
        return branchRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Branch not found with id: " + id));
    }
    
    @PostMapping
    public Branch createBranch(@RequestBody Branch branch){
        return branchRepository.save(branch);
    }
}
