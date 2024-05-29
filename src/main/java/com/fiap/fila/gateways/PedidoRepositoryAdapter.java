package com.fiap.fila.gateways;

import com.fiap.fila.interfaces.gateways.IPedidoRepositoryPort;
import com.fiap.fila.utils.enums.StatusPedido;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;


import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PedidoRepositoryAdapter implements IPedidoRepositoryPort {

    private final SqsTemplate sqsTemplate;

    @Value("${queue.update.pedido}")
    private String nomeDaFila;

    @Override
    public void atualizarPedido(UUID idPedido, StatusPedido statusPedido) {
        this.sqsTemplate.send(nomeDaFila,
                MessageBuilder
                        .withPayload(
        "{\"idPedido\":\"" + idPedido + "\", \"statusPedido\":\"" + statusPedido + "\", \"tipoAtualizacao\":\"F\" }")
                        .build());
    }
}
