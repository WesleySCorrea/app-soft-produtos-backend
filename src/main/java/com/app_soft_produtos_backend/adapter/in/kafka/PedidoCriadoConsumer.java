package com.app_soft_produtos_backend.adapter.in.kafka;

import com.app_soft_produtos_backend.adapter.in.kafka.event.PedidoCriadoEvent;
import com.app_soft_produtos_backend.application.port.in.ProdutoUseCase;
import com.app_soft_produtos_backend.application.port.in.dto.request.ItemCommand;
import com.app_soft_produtos_backend.application.port.in.dto.request.PedidoCommand;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoCriadoConsumer {

    private final ProdutoUseCase produtoUseCase;

    public PedidoCriadoConsumer(ProdutoUseCase produtoUseCase) {
        this.produtoUseCase = produtoUseCase;
    }

    @KafkaListener(topics = "pedido-criado")
    public void consumir(PedidoCriadoEvent event) {

        List<ItemCommand> itens = event.itens().stream()
                .map(i -> new ItemCommand(i.produtoId(), i.quantidade()))
                .toList();

        produtoUseCase.reservar(new PedidoCommand(event.pedidoId(), itens));
    }
}
