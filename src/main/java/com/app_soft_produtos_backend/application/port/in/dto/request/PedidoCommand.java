package com.app_soft_produtos_backend.application.port.in.dto.request;

import java.util.List;

public record PedidoCommand(
        Long pedidoId,
        List<ItemCommand> itens
) {
}