package com.example.microservico.agendador;

import com.example.microservico.repository.PropostaRepository;
import com.example.microservico.service.NotificacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropostaSemIntegracao {

    @Autowired
    private PropostaRepository propostaRepository;
    @Autowired
    private NotificacaoService notificacaoService;
    @Value("${rabbitmq.propostapendente.exchange}")
    private String exchange;

    public void buscarPropostasNaoIntegradas() {
        propostaRepository.findByIntegradaIsFalse().forEach(proposta -> {
            try {
                notificacaoService.notificar(proposta, exchange);
                proposta.setIntegrada(true);
                propostaRepository.save(proposta);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        });
    }
}
