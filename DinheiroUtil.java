package br.icev.vendas;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class UtilDinheiro {
    private UtilDinheiro() {}
    public static BigDecimal arredondar2(BigDecimal valor) {
        if (valor == null) return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        return valor.setScale(2, RoundingMode.HALF_UP);
    }
}
