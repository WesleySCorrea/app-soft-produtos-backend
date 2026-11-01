package com.app_soft_produtos_backend.application.port.out.dto;

import java.math.BigDecimal;

public record ItemReservado(
        Long produtoId,
        String nome,
        BigDecimal preco,
        int quantidade
) {
}