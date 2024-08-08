package com.example.analiseDeCredito.listener;


import com.example.Notificacao.domain.Proposta;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PropostaEmAnaliseListener {



    //Listener(ouvinte) que irá receber a mensagem da fila proposta.pendente
    @RabbitListener(queues = "${rabbitmq.queue.proposta.pendente}")
    public void propostaEmAnalise(Proposta proposta) {

    }
}
