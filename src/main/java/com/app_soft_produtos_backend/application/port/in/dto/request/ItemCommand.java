package com.app_soft_produtos_backend.application.port.in.dto.request;

public record ItemCommand(
        Long produtoId,
        int quantidade
) {
}