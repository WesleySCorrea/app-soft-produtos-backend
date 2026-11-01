package com.app_soft_produtos_backend.adapter.in.web;

import com.app_soft_produtos_backend.application.port.in.ProdutoUseCase;
import com.app_soft_produtos_backend.application.port.in.dto.response.ProdutoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoUseCase produtoUseCase;

    public ProdutoController(ProdutoUseCase produtoUseCase) {
        this.produtoUseCase = produtoUseCase;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ProdutoResponse>> buscarProdutos(
            @RequestParam String nome
    ) {

        List<ProdutoResponse> response = produtoUseCase.buscarProdutos(nome);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
