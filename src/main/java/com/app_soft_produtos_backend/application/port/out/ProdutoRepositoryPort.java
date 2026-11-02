package com.app_soft_produtos_backend.application.port.out;

import com.app_soft_produtos_backend.domain.Produto;

import java.util.List;

public interface ProdutoRepositoryPort {

    List<Produto> buscarPorIds(List<Long> id);

    Produto salvar(Produto produto);

    List<Produto> buscarProdutos(String nome);

    Produto buscarPorId(Long produtoId);
}
