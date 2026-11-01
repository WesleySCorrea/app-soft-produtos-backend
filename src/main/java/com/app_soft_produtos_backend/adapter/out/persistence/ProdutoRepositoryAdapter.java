package com.app_soft_produtos_backend.adapter.out.persistence;

import com.app_soft_produtos_backend.adapter.out.persistence.mapper.ProdutoPersistenceMapper;
import com.app_soft_produtos_backend.adapter.out.persistence.repository.ProdutoJpaRepository;
import com.app_soft_produtos_backend.application.port.out.ProdutoRepositoryPort;
import com.app_soft_produtos_backend.domain.Produto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProdutoRepositoryAdapter implements ProdutoRepositoryPort {

    private final ProdutoJpaRepository jpaRepository;
    private final ProdutoPersistenceMapper mapper;

    public ProdutoRepositoryAdapter(ProdutoJpaRepository jpaRepository, ProdutoPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public List<Produto> buscarPorIds(List<Long> ids) {

        return jpaRepository.findAllById(ids).stream().map(mapper::toDomain).toList();
    }

    @Override
    public Produto salvar(Produto produto) {

        return mapper.toDomain(jpaRepository.save(mapper.toEntity(produto)));
    }

    @Override
    public List<Produto> buscarProdutos(String nome) {

        return jpaRepository.findByNomeContainingIgnoreCase(nome).stream()
                .map(mapper::toDomain)
                .toList();
    }
}
