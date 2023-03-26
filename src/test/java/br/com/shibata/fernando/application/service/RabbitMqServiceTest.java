package br.com.shibata.fernando.application.service;

import br.com.shibata.fernando.application.entity.Pauta;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
class RabbitMqServiceTest {

    @InjectMocks
    private RabbitMqService service;

    @Mock
    private RabbitTemplate template;

    @Test
    void test() {
        assertDoesNotThrow(() -> service.enviarMensagem(new Pauta()));
    }
}