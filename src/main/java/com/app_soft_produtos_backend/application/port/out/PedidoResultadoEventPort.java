package com.app_soft_produtos_backend.application.port.out;

import com.app_soft_produtos_backend.application.port.out.dto.PedidoResultado;

public interface PedidoResultadoEventPort {

    void publicarPedidoResultado(PedidoResultado resultado);
}
