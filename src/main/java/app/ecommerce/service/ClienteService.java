package app.ecommerce.service;

import org.springframework.stereotype.Service;

import app.ecommerce.model.Cliente;
import app.ecommerce.repository.ClienteRepository;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente findByEmail(String email) {
        return clienteRepository.findByEmail(email).orElse(null);
    }
}
