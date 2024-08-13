package com.example.microservico.listener;

import com.example.microservico.entity.Proposta;
import com.example.microservico.repository.PropostaRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PropostaConcluidaListener {

    @Autowired
    private PropostaRepository propostaRepository;

    @RabbitListener(queues = "${rabbitmq.queue.proposta.concluida}")
    public void proprostaConcluida(Proposta proposta) {
        propostaRepository.save(proposta);
    }
}
