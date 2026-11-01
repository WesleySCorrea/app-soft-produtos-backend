package com.app_soft_produtos_backend.adapter.in.kafka.event;

import java.util.List;

public record PedidoCriadoEvent(
        Long pedidoId,
        List<ItemPedidoEvent> itens
) {
}
