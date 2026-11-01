package com.app_soft_produtos_backend.application.port.out.dto;

import java.util.List;

public record PedidoResultado(
        Long pedidoId,
        boolean reservado,
        String motivo,
        List<ItemReservado> itens
) {

    public static PedidoResultado reservado(Long pedidoId, List<ItemReservado> itens) {

        return new PedidoResultado(pedidoId, true, null, itens);
    }

    public static PedidoResultado recusado(Long pedidoId, String motivo) {

        return new PedidoResultado(pedidoId, false, motivo, List.of());
    }
}
