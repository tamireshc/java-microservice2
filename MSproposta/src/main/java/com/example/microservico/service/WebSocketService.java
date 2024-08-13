package com.example.microservico.service;

import com.example.microservico.dto.PropostaResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Service
public class WebSocketService {

    @Autowired
    private  SimpMessagingTemplate simpMessagingTemplate;

    public void notificar(PropostaResponseDTO proposta) {
        simpMessagingTemplate.convertAndSend("/propostas", proposta);
    }

}
