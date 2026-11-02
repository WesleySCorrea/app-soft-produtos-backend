package com.app_soft_produtos_backend.adapter.in.web;

import com.app_soft_produtos_backend.adapter.in.web.dto.request.AtualizarProdutoRequest;
import com.app_soft_produtos_backend.adapter.in.web.dto.request.CriarProdutoRequest;
import com.app_soft_produtos_backend.application.port.in.ProdutoUseCase;
import com.app_soft_produtos_backend.application.port.in.dto.request.AtualizarProdutoCommand;
import com.app_soft_produtos_backend.application.port.in.dto.request.CriarProdutoCommand;
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

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProdutoResponse> criarProduto(
            @RequestBody CriarProdutoRequest request
    ) {

        ProdutoResponse response = produtoUseCase.criarProduto(
                new CriarProdutoCommand(
                        request.nome(),
                        request.descricao(),
                        request.preco(),
                        request.estoque(),
                        request.ativo()
                )
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{produtoId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProdutoResponse> atualizarProduto(
            @PathVariable Long produtoId,
            @RequestBody AtualizarProdutoRequest request
    ) {
        ProdutoResponse response = produtoUseCase.atualizarProduto(
                produtoId,
                new AtualizarProdutoCommand(
                        request.nome(),
                        request.descricao(),
                        request.preco(),
                        request.estoque(),
                        request.ativo()
                )
        );
        return ResponseEntity.ok(response);
    }
}
