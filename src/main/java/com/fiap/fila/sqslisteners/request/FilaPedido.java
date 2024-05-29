package com.fiap.fila.sqslisteners.request;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class FilaPedido {
    private UUID idPedido;
    private UUID idCliente;
}
