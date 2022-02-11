package com.banco.services;

import com.banco.entities.Cliente;
import com.banco.repositories.ClienteRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository cRepository;

    public ClienteService(ClienteRepository cRepository) {
        this.cRepository = cRepository;
    }

    public Cliente addCliente(Cliente c) {
        c.setNome(c.getNome().substring(0,1).toUpperCase() + c.getNome().substring(1));
        return cRepository.save(c);
    }

    public Cliente getCliente(Long id) {
        Optional<Cliente> cOptional  = cRepository.findById(id);
        if (cOptional.isPresent()) {
            return cOptional.get();
        }
        throw new EntityNotFoundException("Cliente não encontrado");
    }

    public List<Cliente> listaClientes() {
        return this.cRepository.findAll();
    }

    public List<Cliente> listaClientesPorIdBanco(Long idBanco) {
        return this.cRepository.findByBanco_id(idBanco);
    }

    public Cliente delCliente(Long id) {
        Cliente cTemp = new Cliente();
        cTemp = cRepository.findById(id).get();
        cRepository.deleteById(id);
        return cTemp;
    }
}
