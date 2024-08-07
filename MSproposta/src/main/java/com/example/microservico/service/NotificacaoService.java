package com.example.microservico.service;

import com.example.microservico.dto.PropostaResponseDTO;
import com.example.microservico.entity.Proposta;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificacaoService {
    //@Bean de rabbitTemplate que é criado pelo Spring, mas utilizamos o personalizado criado em RabbitMQConfiguration
    private RabbitTemplate rabbitTemplate;

    public void notificar(Proposta proposta, String exchange) {
        rabbitTemplate.convertAndSend(exchange, "", proposta);
    }
}
