package com.app_soft_produtos_backend.application.service;

import com.app_soft_produtos_backend.application.port.in.ProdutoUseCase;
import com.app_soft_produtos_backend.application.port.in.dto.request.ItemCommand;
import com.app_soft_produtos_backend.application.port.in.dto.request.PedidoCommand;
import com.app_soft_produtos_backend.application.port.out.PedidoResultadoEventPort;
import com.app_soft_produtos_backend.application.port.out.ProdutoRepositoryPort;
import com.app_soft_produtos_backend.application.port.out.dto.ItemReservado;
import com.app_soft_produtos_backend.application.port.out.dto.PedidoResultado;
import com.app_soft_produtos_backend.domain.Produto;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProdutoService implements ProdutoUseCase {

    private final ProdutoRepositoryPort produtoRepositoryPort;
    private final PedidoResultadoEventPort pedidoResultadoEventPort;

    public ProdutoService(ProdutoRepositoryPort produtoRepositoryPort, PedidoResultadoEventPort pedidoResultadoEventPort) {
        this.produtoRepositoryPort = produtoRepositoryPort;
        this.pedidoResultadoEventPort = pedidoResultadoEventPort;
    }

    @Override
    @Transactional
    public void reservar(PedidoCommand pedido) {
        List<Long> ids = pedido.itens().stream().map(ItemCommand::produtoId).toList();

        Map<Long, Produto> produtos = produtoRepositoryPort.buscarPorIds(ids).stream()
                .collect(Collectors.toMap(Produto::getId, p -> p));

        for (ItemCommand item : pedido.itens()) {

            Produto produto = produtos.get(item.produtoId());

            if (produto == null || !produto.temEstoque(item.quantidade())) {
                pedidoResultadoEventPort.publicarPedidoResultado(PedidoResultado.recusado(
                        pedido.pedidoId(), "Produto " + item.produtoId() + " sem estoque"));
                return;
            }
        }

        List<ItemReservado> reservados = new ArrayList<>();
        for (ItemCommand item : pedido.itens()) {

            Produto produto = produtos.get(item.produtoId());
            produto.baixarEstoque(item.quantidade());
            produtoRepositoryPort.salvar(produto);

            reservados.add(new ItemReservado(produto.getId(), produto.getNome(), produto.getPreco(), item.quantidade()));
        }

        pedidoResultadoEventPort.publicarPedidoResultado(PedidoResultado.reservado(pedido.pedidoId(), reservados));
    }
}
