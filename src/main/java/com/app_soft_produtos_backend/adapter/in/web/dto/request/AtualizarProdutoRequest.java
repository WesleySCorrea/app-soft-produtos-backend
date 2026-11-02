package com.app_soft_produtos_backend.adapter.in.web.dto.request;

import java.math.BigDecimal;

public record AtualizarProdutoRequest(
        String nome,
        String descricao,
        BigDecimal preco,
        int estoque,
        boolean ativo
) {
}
