package app.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.ecommerce.model.User;
import app.ecommerce.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User getAllUserById(@PathVariable String id){
        return userRepository.findByUsername(id).get();
    }
    
    @PostMapping
    public User createUser(@RequestBody User user){
        return userRepository.save(user);
    }
}
