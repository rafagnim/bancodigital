package com.banco.dto;

public class LancamentoDto {
    private double valor;
    private String tipo;
    private String data;

    public LancamentoDto() {
    }

    public LancamentoDto(double valor, String tipo, String data) {
        this.valor = valor;
        this.tipo = tipo;
        this.data = data;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

