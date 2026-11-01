package com.app_soft_produtos_backend.application.port.in;

import com.app_soft_produtos_backend.application.port.in.dto.request.PedidoCommand;

public interface ProdutoUseCase {

    void reservar(PedidoCommand pedido);
}
