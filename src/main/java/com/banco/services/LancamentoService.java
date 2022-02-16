package com.banco.services;

import com.banco.entities.Lancamento;
import com.banco.repositories.LancamentoRepository;
import org.springframework.stereotype.Service;

@Service
public class LancamentoService {

    private final LancamentoRepository lancamentoRepository;

    public LancamentoService(LancamentoRepository lancamentoRepository) {
        this.lancamentoRepository = lancamentoRepository;
    }

    public void addLancamento(Lancamento l) {
        lancamentoRepository.save(l);
    }
}
