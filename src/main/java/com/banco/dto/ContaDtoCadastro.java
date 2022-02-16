package com.banco.dto;

import com.banco.Enums.TipoConta;

public class ContaDtoCadastro {
    private Long cliente_id;
    private TipoConta tipoconta;

    public ContaDtoCadastro() {
    }

    public ContaDtoCadastro(TipoConta tipoconta) {
        this.tipoconta = tipoconta;
    }

    public Long getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(Long cliente_id) {
        this.cliente_id = cliente_id;
    }

    public TipoConta getTipoconta() {
        return tipoconta;
    }

    public void setTipoconta(TipoConta tipoconta) {
        this.tipoconta = tipoconta;
    }
}


