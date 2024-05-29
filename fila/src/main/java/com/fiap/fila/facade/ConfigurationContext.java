package com.fiap.fila.facade;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.fila.interfaces.gateways.IFilaRepositoryPort;
import com.fiap.fila.interfaces.gateways.IPedidoRepositoryPort;
import com.fiap.fila.interfaces.usecases.IFilaUseCasePort;
import com.fiap.fila.usecases.FilaUseCaseImpl;
import io.awspring.cloud.sqs.config.SqsListenerConfigurer;
import io.awspring.cloud.sqs.config.SqsMessageListenerContainerFactory;
import io.awspring.cloud.sqs.listener.QueueNotFoundStrategy;
import io.awspring.cloud.sqs.listener.acknowledgement.handler.AcknowledgementMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.util.MimeType;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

@Configuration
public class ConfigurationContext {

    @Bean
    public IFilaUseCasePort ifilaUseCasePort(IFilaRepositoryPort filaRepositoryPort, IPedidoRepositoryPort pedidoRepositoryPort) {
        return new FilaUseCaseImpl(filaRepositoryPort, pedidoRepositoryPort);
    }

    @Primary
    @Bean
    public ObjectMapper om() {
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        return om;
    }

    @Bean
    public SqsListenerConfigurer configurer(ObjectMapper objectMapper) {
        var defaultConverter = new MappingJackson2MessageConverter(
                new MimeType("application", "json"),
                new MimeType("application", "*+json"),
                new MimeType("text", "plain")
        );
        defaultConverter.setObjectMapper(objectMapper);
        return registrar -> {
            registrar.manageMessageConverters(converters -> {
                converters.clear();
                converters.add(defaultConverter);
            });
        };
    }

    @Bean
    SqsMessageListenerContainerFactory<Object> defaultSqsListenerContainerFactory(SqsAsyncClient sqsAsyncClient) {
        return SqsMessageListenerContainerFactory
                .builder()
                .configure(options -> options
                        .queueNotFoundStrategy(QueueNotFoundStrategy.CREATE)
                        .acknowledgementMode(AcknowledgementMode.ALWAYS)
                )
                .sqsAsyncClient(sqsAsyncClient)
                .build();
    }

}
