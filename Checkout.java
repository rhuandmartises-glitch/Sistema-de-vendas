package br.icev.vendas;

import br.icev.vendas.excecoes.ErroPagamentoException;
import br.icev.vendas.excecoes.QuantidadeInvalidaException;
import br.icev.vendas.excecoes.SemEstoqueException;

import java.math.BigDecimal;
import java.util.Map;

public class Checkout {

    private final Estoque estoque;
    private final GatewayPagamento gateway;

    public Checkout(Estoque estoque, GatewayPagamento gateway) {
        this.estoque = estoque;
        this.gateway = gateway;
    }

    public Pedido finalizar(Carrinho carrinho, PoliticaDesconto politica)
            throws SemEstoqueException, QuantidadeInvalidaException, ErroPagamentoException {

        // 1. Reservar todos os itens no estoque
        for (Map.Entry<String, Integer> e : carrinho.getItensPorCodigo().entrySet()) {
            String cod = e.getKey();
            int qtd = e.getValue();
            estoque.reservar(cod, qtd);
        }

        // 2. Calcular total com desconto
        BigDecimal total = carrinho.getTotalCom(politica);

        // 3. Realizar pagamento com gateway
        String autorizacao = gateway.pagar(total);

        // 4. Criar pedido final
        return new Pedido(
                carrinho.getItensPorCodigo(),
                total,
                autorizacao,
                Pedido.Status.PAGO
        );
    }
}
