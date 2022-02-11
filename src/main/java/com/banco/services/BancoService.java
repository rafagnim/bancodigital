package com.banco.services;

import com.banco.entities.Banco;
import com.banco.repositories.BancoRepository;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class BancoService {

    private final BancoRepository bRepository;

    public BancoService(BancoRepository bRepository) {
        this.bRepository = bRepository;
    }

    public Banco addBanco(Banco b) {
        b.setNome(b.getNome().substring(0,1).toUpperCase() + b.getNome().substring(1));
        return bRepository.save(b);
    }

    public Banco getBanco(Long id) {
        Optional<Banco> bOptional  = bRepository.findById(id);
        if (bOptional.isPresent()) {
            return bOptional.get();
        }
        throw new EntityNotFoundException("Banco não encontrado");
    }

    public List<Banco> listaBancos() {
        return this.bRepository.findAll();
    }

    public Banco delBanco(Long id) {
        Banco bTemp = new Banco();
        bTemp.setNome(bRepository.findById(id).get().getNome());
        bRepository.deleteById(id);
        return bTemp;
    }
}
