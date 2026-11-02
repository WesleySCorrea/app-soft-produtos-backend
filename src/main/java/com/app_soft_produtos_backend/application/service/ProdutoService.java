package com.app_soft_produtos_backend.application.service;

import com.app_soft_produtos_backend.application.port.in.ProdutoUseCase;
import com.app_soft_produtos_backend.application.port.in.dto.request.AtualizarProdutoCommand;
import com.app_soft_produtos_backend.application.port.in.dto.request.CriarProdutoCommand;
import com.app_soft_produtos_backend.application.port.in.dto.request.ItemCommand;
import com.app_soft_produtos_backend.application.port.in.dto.request.PedidoCommand;
import com.app_soft_produtos_backend.application.port.in.dto.response.ProdutoResponse;
import com.app_soft_produtos_backend.application.port.out.PedidoResultadoEventPort;
import com.app_soft_produtos_backend.application.port.out.ProdutoRepositoryPort;
import com.app_soft_produtos_backend.application.port.out.dto.ItemReservado;
import com.app_soft_produtos_backend.application.port.out.dto.PedidoResultado;
import com.app_soft_produtos_backend.domain.Produto;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    @Override
    public List<ProdutoResponse> buscarProdutos(String nome) {

        List<Produto> produtos = produtoRepositoryPort.buscarProdutos(nome);

        return produtos.stream().map(produto ->
                new ProdutoResponse(
                        produto.getId(),
                        produto.getNome(),
                        produto.getDescricao(),
                        produto.getPreco(),
                        produto.getEstoque(),
                        produto.isAtivo()
                )).toList();
    }

    @Override
    public ProdutoResponse criarProduto(CriarProdutoCommand produtoRequest) {

        Produto produto = Produto.novoProduto(
                produtoRequest.nome(),
                produtoRequest.descricao(),
                produtoRequest.preco(),
                produtoRequest.estoque(),
                produtoRequest.ativo()
        );

        Produto salvo = produtoRepositoryPort.salvar(produto);

        return new ProdutoResponse(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getEstoque(),
                produto.isAtivo()
        );
    }

    @Override
    public ProdutoResponse atualizarProduto(Long produtoId, AtualizarProdutoCommand produtoRequest) {

        Produto produto = produtoRepositoryPort.buscarPorId(produtoId);

        produto.setNome(produtoRequest.nome());
        produto.setDescricao(produtoRequest.descricao());
        produto.setPreco(produtoRequest.preco());
        produto.setEstoque(produtoRequest.estoque());
        produto.setAtivo(produtoRequest.ativo());

        Produto atualizado = produtoRepositoryPort.salvar(produto);

        return new ProdutoResponse(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getEstoque(),
                produto.isAtivo()
        );
    }
}
