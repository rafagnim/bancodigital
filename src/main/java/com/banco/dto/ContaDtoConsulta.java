package com.banco.dto;

import com.banco.Enums.TipoConta;

public class ContaDtoConsulta {
    private int agencia;
    private TipoConta tipo;
    private Long numero;
    private double saldo;
    private boolean ativa;

    public ContaDtoConsulta() {
    }

    public ContaDtoConsulta(int agencia, TipoConta tipo, Long numero, double saldo, boolean ativa) {
        this.agencia = agencia;
        this.tipo = tipo;
        this.numero = numero;
        this.saldo = saldo;
        this.ativa = ativa;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    public int getAgencia() {
        return agencia;
    }

    public void setAgencia(int agencia) {
        this.agencia = agencia;
    }

    public TipoConta getTipo() {
        return tipo;
    }

    public void setTipo(TipoConta tipo) {
        this.tipo = tipo;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return "ContaDtoConsulta{" +
                "agencia=" + agencia +
                ", tipo=" + tipo +
                ", numero=" + numero +
                ", saldo=" + saldo +
                '}';
    }
}
