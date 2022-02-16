package com.banco.entities;

import javax.persistence.*;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public abstract class Conta implements IConta {

    private static final int AGENCIA_PADRAO = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    protected Long id;
    protected int agencia;
    protected double saldo;
    @OneToOne
    protected Cliente cliente;
    boolean ativa;

    protected Conta() {
    }

    public Conta(Cliente cliente) {
        this.agencia = Conta.AGENCIA_PADRAO;
        this.cliente = cliente;
        this.ativa = true;
    }

    @Override
    public void sacar(double valor) {
        if((this.saldo - valor) < 0) {
            throw new IllegalArgumentException("Saldo Insuficiente");
        }
        saldo -= valor;
    }

    @Override
    public void depositar(double valor) {
        saldo += valor;
    }

    @Override
    public void transferir(double valor, IConta contaDestino) {
        if((this.saldo - valor) < 0) {
            throw new IllegalArgumentException("Saldo Insuficiente");
        }
        this.sacar(valor);
        contaDestino.depositar(valor);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAgencia() {
        return agencia;
    }

    public void setAgencia(int agencia) {
        this.agencia = agencia;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    protected void imprimirInfosComuns() {
        //System.out.println(String.format("Titular: %s", this.cliente.getNome()));
        //System.out.println(String.format("Agencia: %d", this.agencia));
        //System.out.println(String.format("Numero: %d", this.numero));
        //System.out.println(String.format("Saldo: %.2f", this.saldo));
    }
}
