package com.app_soft_produtos_backend.adpter.in.web;

import com.app_soft_produtos_backend.adapter.in.web.ProdutoController;
import com.app_soft_produtos_backend.application.port.in.ProdutoUseCase;
import com.app_soft_produtos_backend.application.port.in.dto.response.ProdutoResponse;
import com.app_soft_produtos_backend.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProdutoController.class)
@Import(SecurityConfig.class)
public class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProdutoUseCase produtoUseCase;

    @MockitoBean
    private JwtDecoder jwtDecoder;

    @Test
    void buscarProdutosTest() throws Exception {

        ProdutoResponse produto = new ProdutoResponse(1L, "Notebook", "Notebook marca Acer", new BigDecimal("3500.00"), 10, true);

        when(produtoUseCase.buscarProdutos("Note")).thenReturn(List.of(produto));

        mockMvc.perform(get("/produtos")
                .param("nome", "Note")
                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_USER"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].produtoId").value(1))
                .andExpect(jsonPath("$[0].nome").value("Notebook"));
    }

    @Test
    void buscarProdutosSemTokenTest() throws Exception {

        mockMvc.perform(get("/produtos")
                .param("nome", "Note"))
                .andExpect(status().isUnauthorized());

        verify(produtoUseCase, never()).buscarProdutos(anyString());
    }

    @Test
    void criarProdutoTest() throws Exception {

        ProdutoResponse response = new ProdutoResponse(1L, "Notebook", "Notebook marca Acer", new BigDecimal("3500.00"), 10, true);

        when(produtoUseCase.criarProduto(any())).thenReturn(response);

        mockMvc.perform(post("/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\":\"Notebook\",\"descricao\":\"Notebook marca Acer\",\"preco\":3500.00,\"estoque\":10,\"ativo\":true}")
                .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ADMIN"))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.produtoId").value(1))
                .andExpect(jsonPath("$.nome").value("Notebook"));
    }
}
