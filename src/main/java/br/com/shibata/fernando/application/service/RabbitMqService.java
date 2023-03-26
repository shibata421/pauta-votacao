package br.com.shibata.fernando.application.service;

import br.com.shibata.fernando.application.entity.Pauta;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class RabbitMqService {

    private static final String EXCHANGE = "exchange.pauta";
    private static final String ROUTING_KEY = "rk.pauta";
    private final RabbitTemplate rabbitTemplate;

    public void enviarMensagem(Pauta pauta) {
        log.info("Enviando pauta");
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, pauta);
    }

}
