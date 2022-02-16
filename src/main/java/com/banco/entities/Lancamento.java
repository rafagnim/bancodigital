package com.banco.entities;

import javax.persistence.*;

@Entity
public class Lancamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private double valor;
    private Long contaorigem;
    private Long contadestino;
    private String tipo;

    public Lancamento() {
    }

    public Lancamento(Long id, double valor, Long contaorigem, Long contadestino, String tipo) {
        this.id = id;
        this.valor = valor;
        this.contaorigem = contaorigem;
        this.contadestino = contadestino;
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Long getContaorigem() {
        return contaorigem;
    }

    public void setContaorigem(Long contaorigem) {
        this.contaorigem = contaorigem;
    }

    public Long getContadestino() {
        return contadestino;
    }

    public void setContadestino(Long contadestino) {
        this.contadestino = contadestino;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Lancamento{" +
                "id=" + id +
                ", valor=" + valor +
                ", contaorigem=" + contaorigem +
                ", contadestino=" + contadestino +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}