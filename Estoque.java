package br.icev.vendas;

import br.icev.vendas.excecoes.QuantidadeInvalidaException;
import br.icev.vendas.excecoes.SemEstoqueException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Estoque {
    private final Map<String, Integer> disponiveis = new ConcurrentHashMap<>();

    public void adicionarEstoque(String codigo, int quantidade) throws QuantidadeInvalidaException {
        if (quantidade <= 0) throw new QuantidadeInvalidaException("Quantidade deve ser maior que zero");
        disponiveis.merge(codigo, quantidade, Integer::sum);
    }

    public int getDisponivel(String codigo) {
        return disponiveis.getOrDefault(codigo, 0);
    }

    public void reservar(String codigo, int quantidade)
            throws SemEstoqueException, QuantidadeInvalidaException {
        if (quantidade <= 0) throw new QuantidadeInvalidaException("Quantidade deve ser maior que zero");
        synchronized (disponiveis) {
            int atual = disponiveis.getOrDefault(codigo, 0);
            if (atual < quantidade) throw new SemEstoqueException("Sem estoque suficiente para " + codigo);
            disponiveis.put(codigo, atual - quantidade);
        }
    }
}
