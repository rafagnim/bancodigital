package com.banco.entities;

import javax.persistence.Entity;

@Entity
public class ContaPoupanca extends Conta {

    public ContaPoupanca() {
    }

    public ContaPoupanca(Cliente cliente) {
        super(cliente);
    }

    @Override
    public void imprimirExtrato() {
        System.out.println("=== Extrato Conta Poupan�a ===");
        super.imprimirInfosComuns();
    }

    @Override
    public String toString() {
        return "ContaPoupanca{" +
                "id=" + id +
                ", agencia=" + agencia +
                ", saldo=" + saldo +
                ", cliente=" + cliente +
                ", ativa=" + ativa +
                '}';
    }
}
