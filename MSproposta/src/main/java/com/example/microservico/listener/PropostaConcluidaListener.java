package com.example.microservico.listener;

import com.example.microservico.dto.PropostaResponseDTO;
import com.example.microservico.entity.Proposta;
import com.example.microservico.repository.PropostaRepository;
import com.example.microservico.service.PropostaService;
import com.example.microservico.service.WebSocketService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PropostaConcluidaListener {

    @Autowired
    private PropostaRepository propostaRepository;

    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    private PropostaService propostaService;

    @RabbitListener(queues = "${rabbitmq.queue.proposta.concluida}")
    public void proprostaConcluida(Proposta proposta) {
        propostaRepository.save(proposta);
        PropostaResponseDTO responseDTO = propostaService.converterPropostaParaPropostaResponseDTO(proposta);
        webSocketService.notificar(responseDTO);
    }
}
