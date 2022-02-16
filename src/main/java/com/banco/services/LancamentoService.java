package com.banco.services;

import com.banco.dto.LancamentoDto;
import com.banco.entities.Lancamento;
import com.banco.repositories.LancamentoRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LancamentoService {

    private final LancamentoRepository lancamentoRepository;

    public LancamentoService(LancamentoRepository lancamentoRepository) {
        this.lancamentoRepository = lancamentoRepository;
    }

    public void addLancamento(Lancamento l) {
        l.setData(LocalDate.now());
        lancamentoRepository.save(l);
    }

    public List<LancamentoDto> extratoConta(Long idContaOrigem) {
        return
                lancamentoRepository.findByContaorigem(idContaOrigem).stream().map(
                        (l) -> {
                            String dia = l.getData().getDayOfMonth() < 10 ? "0" + l.getData().getDayOfMonth() : "" + l.getData().getDayOfMonth();
                            String mes = l.getData().getMonthValue() < 10 ? "0" + l.getData().getMonthValue() : "" + l.getData().getMonthValue();
                            String data = dia + "/" + mes + "/" + l.getData().getYear();
                            return new LancamentoDto(l.getValor(), l.getTipo(), data);
                        }
                ).collect(Collectors.toList());
    }
}
