package app.ecommerce.modules.inventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.ecommerce.modules.inventory.model.Categoria;
import app.ecommerce.modules.inventory.repository.CategoryRepository;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public List<Categoria> getAllCategoria(){
        return categoryRepository.findAll();
    }

    @GetMapping("/{id}")
    public Categoria getCategoriaById(@PathVariable String id) {
        return categoryRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Role not found with id: " + id));
    }

    @PostMapping
    public Categoria createCategoria(@RequestBody Categoria categoria){
        return categoryRepository.save(categoria);
    }
}
