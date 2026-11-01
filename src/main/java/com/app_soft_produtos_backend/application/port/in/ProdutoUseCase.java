package com.app_soft_produtos_backend.application.port.in;

import com.app_soft_produtos_backend.application.port.in.dto.request.PedidoCommand;
import com.app_soft_produtos_backend.application.port.in.dto.response.ProdutoResponse;

import java.util.List;

public interface ProdutoUseCase {

    void reservar(PedidoCommand pedido);

    List<ProdutoResponse> buscarProdutos(String nome);
}
