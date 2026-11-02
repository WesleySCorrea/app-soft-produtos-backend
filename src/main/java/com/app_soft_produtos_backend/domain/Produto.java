package com.app_soft_produtos_backend.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Produto {
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private int estoque;
    private boolean ativo;
    private LocalDateTime criadoEm = LocalDateTime.now();

    public boolean temEstoque(int quantidade) {
        return ativo && estoque >= quantidade;
    }

    public void baixarEstoque(int quantidade) {
        if (!temEstoque(quantidade)) {
            throw new IllegalStateException("Estoque insuficiente");
        }
        this.estoque -= quantidade;
    }

    public static Produto novoProduto(String nome, String descricao, BigDecimal preco, int estoque, boolean ativo) {

        Produto p = new Produto();
        p.nome = nome;
        p.descricao = descricao;
        p.preco = preco;
        p.estoque = estoque;
        p.ativo = ativo;
        return p;
    }

    public static Produto reconstruir(Long id, String nome, String descricao, BigDecimal preco, int estoque, boolean ativo, LocalDateTime criadoEm) {

        Produto p = new Produto();
        p.id = id;
        p.nome = nome;
        p.descricao = descricao;
        p.preco = preco;
        p.estoque = estoque;
        p.ativo = ativo;
        p.criadoEm = criadoEm;

        return p;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }
}

