package br.icev.vendas;

import br.icev.vendas.excecoes.QuantidadeInvalidaException;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

public class Carrinho {
    // guarda por codigo: produto e quantidade
    private final Map<String, Produto> produtos = new LinkedHashMap<>();
    private final Map<String, Integer> quantidades = new LinkedHashMap<>();

    public void adicionar(Produto produto, int quantidade) throws QuantidadeInvalidaException {
        if (quantidade <= 0) throw new QuantidadeInvalidaException("Quantidade inválida");
        String codigo = produto.getCodigo();
        produtos.putIfAbsent(codigo, produto);
        quantidades.merge(codigo, quantidade, Integer::sum);
    }

    public BigDecimal getSubtotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<String, Integer> e : quantidades.entrySet()) {
            Produto p = produtos.get(e.getKey());
            BigDecimal linha = p.getPrecoUnitario().multiply(new BigDecimal(e.getValue()));
            total = total.add(linha);
        }
        return UtilDinheiro.arredondar2(total);
    }

    public BigDecimal getTotalCom(PoliticaDesconto politica) {
        BigDecimal subtotal = getSubtotal();
        BigDecimal total = politica.aplicar(subtotal);
        if (total == null) total = BigDecimal.ZERO;
        if (total.compareTo(BigDecimal.ZERO) < 0) total = BigDecimal.ZERO;
        return UtilDinheiro.arredondar2(total);
    }

    public int getTotalItens() {
        return quantidades.values().stream().mapToInt(Integer::intValue).sum();
    }

    // utilitário (não necessariamente exigido pelos testes, mas útil)
    public Map<String,Integer> getItensPorCodigo() {
        return new LinkedHashMap<>(quantidades);
    }
}
