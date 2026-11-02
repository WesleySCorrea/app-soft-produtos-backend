package com.app_soft_produtos_backend.application;

import com.app_soft_produtos_backend.application.port.in.dto.response.ProdutoResponse;
import com.app_soft_produtos_backend.application.port.out.PedidoResultadoEventPort;
import com.app_soft_produtos_backend.application.port.out.ProdutoRepositoryPort;
import com.app_soft_produtos_backend.application.service.ProdutoService;
import com.app_soft_produtos_backend.domain.Produto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoRepositoryPort produtoRepositoryPort;

    @Mock
    private PedidoResultadoEventPort pedidoResultadoEventPort;

    @InjectMocks
    private ProdutoService produtoService;

    @Test
    void buscarProdutosTest() {

        Produto notebook = Produto.reconstruir(1L, "Notebook", "Acer", new BigDecimal("3500.00"), 10, true, LocalDateTime.now());

        when(produtoRepositoryPort.buscarProdutos("Note")).thenReturn(List.of(notebook));

        List<ProdutoResponse> resultado = produtoService.buscarProdutos("Note");

        assertThat(resultado).hasSize(1);
        ProdutoResponse response = resultado.get(0);
        assertThat(response.produtoId()).isEqualTo(1L);
        assertThat(response.nome()).isEqualTo("Notebook");
        assertThat(response.preco()).isEqualByComparingTo("3500.00");
        assertThat(response.estoque()).isEqualTo(10);
        assertThat(response.ativo()).isTrue();

        verify(produtoRepositoryPort).buscarProdutos("Note");
    }
}