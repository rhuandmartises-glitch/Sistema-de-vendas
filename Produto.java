package br.icev.vendas;

import java.math.BigDecimal;
import java.util.Objects;

public class Produto {
    private final String codigo;
    private final String nome;
    private final BigDecimal precoUnitario;

    public Produto(String codigo, String nome, BigDecimal precoUnitario) {
        if (codigo == null || nome == null || precoUnitario == null) {
            throw new IllegalArgumentException("Argumentos não podem ser nulos");
        }
        this.codigo = codigo;
        this.nome = nome;
        this.precoUnitario = precoUnitario;
    }

    public String getCodigo() { return codigo; }
    public String getNome() { return nome; }
    public BigDecimal getPrecoUnitario() { return precoUnitario; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Produto)) return false;
        Produto produto = (Produto) o;
        return Objects.equals(codigo, produto.codigo);
    }
    @Override
    public int hashCode() { return Objects.hash(codigo); }
}
