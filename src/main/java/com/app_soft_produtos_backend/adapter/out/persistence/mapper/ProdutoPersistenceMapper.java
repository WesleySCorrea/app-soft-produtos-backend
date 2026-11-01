package com.app_soft_produtos_backend.adapter.out.persistence.mapper;

import com.app_soft_produtos_backend.adapter.out.persistence.entity.ProdutoEntity;
import com.app_soft_produtos_backend.domain.Produto;
import org.springframework.stereotype.Component;

@Component
public class ProdutoPersistenceMapper {

    public ProdutoEntity toEntity(Produto produto) {

        ProdutoEntity entity = new ProdutoEntity();
        entity.setId(produto.getId());
        entity.setNome(produto.getNome());
        entity.setDescricao(produto.getDescricao());
        entity.setPreco(produto.getPreco());
        entity.setEstoque(produto.getEstoque());
        entity.setAtivo(produto.isAtivo());
        entity.setCriadoEm(produto.getCriadoEm());
        return entity;
    }

    public Produto toDomain(ProdutoEntity entity) {

        return Produto.reconstruir(
                entity.getId(),
                entity.getNome(),
                entity.getDescricao(),
                entity.getPreco(),
                entity.getEstoque(),
                entity.isAtivo(),
                entity.getCriadoEm()
        );
    }
}
