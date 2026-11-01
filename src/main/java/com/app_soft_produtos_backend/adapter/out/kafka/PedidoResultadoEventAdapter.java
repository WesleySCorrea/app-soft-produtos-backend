package com.app_soft_produtos_backend.adapter.out.kafka;

import com.app_soft_produtos_backend.adapter.out.kafka.event.ItemReservadoEvent;
import com.app_soft_produtos_backend.adapter.out.kafka.event.PedidoResultadoEvent;
import com.app_soft_produtos_backend.application.port.out.PedidoResultadoEventPort;
import com.app_soft_produtos_backend.application.port.out.dto.PedidoResultado;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoResultadoEventAdapter implements PedidoResultadoEventPort {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public PedidoResultadoEventAdapter(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publicarPedidoResultado(PedidoResultado resultado) {

        List<ItemReservadoEvent> itens = resultado.itens().stream()
                .map(i -> new ItemReservadoEvent(i.produtoId(), i.nome(), i.preco(), i.quantidade()))
                .toList();

        PedidoResultadoEvent event = new PedidoResultadoEvent(
                resultado.pedidoId(), resultado.reservado(), resultado.motivo(), itens);

        kafkaTemplate.send("pedido-resultado", resultado.pedidoId().toString(), event);
    }
}
