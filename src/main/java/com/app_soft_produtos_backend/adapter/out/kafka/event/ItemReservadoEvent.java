package com.app_soft_produtos_backend.adapter.out.kafka.event;

import java.math.BigDecimal;

public record ItemReservadoEvent(
        Long produtoId,
        String nome,
        BigDecimal preco,
        int quantidade
) {
}