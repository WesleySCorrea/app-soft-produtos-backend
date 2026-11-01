package com.app_soft_produtos_backend.application.port.in.dto.response;

import java.math.BigDecimal;

public record ProdutoResponse(
    Long produtoId,
    String nome,
    String descricao,
    BigDecimal preco,
    int estoque
) {
}
