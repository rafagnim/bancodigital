package com.banco.entities;

import javax.persistence.*;
import java.time.LocalDate;

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
    private LocalDate data;

    public Lancamento() {
    }

    public Lancamento(Long id, double valor, Long contaorigem, Long contadestino, String tipo, LocalDate data) {
        this.id = id;
        this.valor = valor;
        this.contaorigem = contaorigem;
        this.contadestino = contadestino;
        this.tipo = tipo;
        this.data = data;
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

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}