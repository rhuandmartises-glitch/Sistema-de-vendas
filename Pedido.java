package br.icev.vendas;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.LinkedHashMap;

public class Pedido {
    public enum Status { PAGO }

    private final Map<String, Integer> itensPorCodigo;
    private final BigDecimal totalPago;
    private final String codigoAutorizacao;
    private final Status status;

    public Pedido(Map<String, Integer> itensPorCodigo, BigDecimal totalPago,
                  String codigoAutorizacao, Status status) {
        this.itensPorCodigo = new LinkedHashMap<>(itensPorCodigo);
        this.totalPago = totalPago;
        this.codigoAutorizacao = codigoAutorizacao;
        this.status = status;
    }

    public BigDecimal getTotalPago() { return totalPago; }
    public String getCodigoAutorizacao() { return codigoAutorizacao; }
    public Status getStatus() { return status; }
    public int getQuantidadeItem(String codigo) {
        return itensPorCodigo.getOrDefault(codigo, 0);
    }
}
