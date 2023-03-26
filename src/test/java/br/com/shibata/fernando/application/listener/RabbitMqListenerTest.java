package br.com.shibata.fernando.application.listener;

import br.com.shibata.fernando.application.entity.Pauta;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RabbitMqListenerTest {

    @Test
    void deveOuvirFila() {
        assertDoesNotThrow(() -> new RabbitMqListener().listen(new Pauta()));
    }

}