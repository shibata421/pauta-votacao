package br.com.shibata.fernando.application.listener;

import br.com.shibata.fernando.application.entity.Pauta;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RabbitMqListener {

    @RabbitListener(queues = "queue.pauta")
    public void listen(Pauta msg) {
        log.info("Quantidade de votos sim {}", msg.getSomaSim());
        log.info("Quantidade de votos não {}", msg.getSomaNao());
    }
}
