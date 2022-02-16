package com.banco.repositories;

import com.banco.entities.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContaRepository<T extends Conta> extends JpaRepository<T, Long> {
    List<Conta> findByCliente_id(Long idCliente);
}
