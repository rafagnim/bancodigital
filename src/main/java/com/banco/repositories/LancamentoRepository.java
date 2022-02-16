package com.banco.repositories;

import com.banco.entities.Conta;
import com.banco.entities.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {
    List<Lancamento> findByContaorigem(Long idContaOrigem);
}
