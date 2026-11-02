package com.app_soft_produtos_backend.application.port.in.dto.request;

import java.math.BigDecimal;

public record CriarProdutoCommand(
        String nome,
        String descricao,
        BigDecimal preco,
        int estoque,
        boolean ativo
) {
}
