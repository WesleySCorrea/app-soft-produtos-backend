package com.app_soft_produtos_backend.adapter.in.kafka.event;

public record ItemPedidoEvent(
        Long produtoId,
        int quantidade
) {
}