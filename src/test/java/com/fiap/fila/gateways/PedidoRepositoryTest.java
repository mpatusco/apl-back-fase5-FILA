package com.fiap.fila.gateways;

import com.fiap.fila.interfaces.gateways.IPedidoRepositoryPort;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class PedidoRepositoryTest {

    private IPedidoRepositoryPort pedidoRepository;

    @Mock
    private SqsTemplate sqsTemplate;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        pedidoRepository = new PedidoRepositoryAdapter(this.sqsTemplate);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

//    @Test
//    @Severity(SeverityLevel.CRITICAL)
//    @Description("Deve atualizar status do pedido")
//    void deveAtualizarPedido() throws Exception {
//        StatusPedido.P.getDescricao();
//        pedidoRepository.atualizarPedido(UUID.randomUUID(), StatusPedido.E);
//        verify(repository, times(1)).atualizarPedido(any(UUID.class), any(StatusPedido.class));
//    }

}
