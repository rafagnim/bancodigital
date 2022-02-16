package com.banco.dto;

import com.banco.Enums.TipoConta;
import com.banco.entities.Conta;

public class MapperContaDto {

    public static ContaDtoConsulta fromEntity(Conta c) {

        TipoConta tipo = getTipoConta(c);
        return new ContaDtoConsulta(c.getAgencia(), tipo, c.getId(), c.getSaldo(), c.isAtiva());
    }

    public static TipoConta getTipoConta(Conta c) {
        TipoConta tipo;
        if (c.getClass().getSimpleName().toUpperCase().equals("CONTACORRENTE")) {
            tipo = TipoConta.CORRENTE;
        } else {
            tipo = TipoConta.POUPANCA;
        }
        return tipo;
    }
}
