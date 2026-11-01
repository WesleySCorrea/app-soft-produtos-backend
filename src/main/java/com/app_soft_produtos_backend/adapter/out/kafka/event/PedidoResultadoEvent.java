package com.app_soft_produtos_backend.adapter.out.kafka.event;

import java.util.List;

public record PedidoResultadoEvent(
        Long pedidoId,
        boolean reservado,
        String motivo,
        List<ItemReservadoEvent> itens
) {
}