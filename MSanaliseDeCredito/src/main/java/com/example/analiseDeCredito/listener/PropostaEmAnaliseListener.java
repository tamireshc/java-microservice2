package com.example.analiseDeCredito.listener;


import com.example.analiseDeCredito.domain.Proposta;
import com.example.analiseDeCredito.service.AnaliseCreditoService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class PropostaEmAnaliseListener {

    @Autowired
    private AnaliseCreditoService analiseCreditoService;

    //Listener(ouvinte) que irá receber a mensagem da fila proposta.pendente
    @RabbitListener(queues = "${rabbitmq.queue.proposta.pendente}")
    public void propostaEmAnalise(Proposta proposta) {
        analiseCreditoService.analizar(proposta);
    }
}
