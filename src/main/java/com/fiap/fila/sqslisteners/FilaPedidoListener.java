package com.fiap.fila.sqslisteners;

import com.fiap.fila.entities.ItemFila;
import com.fiap.fila.interfaces.usecases.IFilaUseCasePort;
import com.fiap.fila.sqslisteners.request.FilaPedido;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FilaPedidoListener {

    private final IFilaUseCasePort filaUseCasePort;

    @SqsListener(queueNames = "${queue.fila.pedido}", factory = "defaultSqsListenerContainerFactory")
    public void listenNovosPedidos(FilaPedido message) {
                var itemFila = ItemFila.builder()
                .idPedido(message.getIdPedido())
                .idCliente(message.getIdCliente())
                .build();
        this.filaUseCasePort.inserirPedidoNaFila(itemFila);
    }
}
