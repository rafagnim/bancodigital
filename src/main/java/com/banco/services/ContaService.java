package com.banco.services;

import com.banco.Enums.TipoConta;
import com.banco.dto.ContaDtoConsulta;
import com.banco.dto.MapperContaDto;
import com.banco.entities.Conta;
import com.banco.repositories.ContaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContaService {

    private final ContaRepository<Conta> contaRepository;

    public ContaService(ContaRepository<Conta> contaRepository) {
        this.contaRepository = contaRepository;
    }

    public Conta addConta(Conta conta) {
        return contaRepository.save(conta);
    }

    public Conta putConta(Conta conta) {
        return contaRepository.save(conta);
    }

    public Conta getConta(Long id) {
        return contaRepository.getById(id);
    }

    public List<ContaDtoConsulta> listaContasCliente(Long idCliente) {
        return contaRepository.findByCliente_id(idCliente).stream().map(
                (c) -> {
                    TipoConta tipo = MapperContaDto.getTipoConta(c);
                    if (c.getClass().getSimpleName().toUpperCase().equals("CONTACORRENTE")) {
                        tipo = TipoConta.CORRENTE;
                    } else {
                        tipo = TipoConta.POUPANCA;
                    }
                    return new ContaDtoConsulta(c.getAgencia(), tipo, c.getId(), c.getSaldo(), c.isAtiva());
                }
        ).collect(Collectors.toList());
    }

    public Conta encerrarConta(Long idConta) {
        Conta c;
        c = contaRepository.getById(idConta);
        if(c.getSaldo() == 0 && c.isAtiva()) {
            c.setAtiva(false);
            contaRepository.save(c);
            return c;
        } else {
            throw new IllegalArgumentException("A conta deve estar zerada para poder ser encerrada");
        }
    }
}