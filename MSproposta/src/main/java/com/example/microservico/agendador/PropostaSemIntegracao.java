package com.example.microservico.agendador;

import com.example.microservico.repository.PropostaRepository;
import com.example.microservico.service.NotificacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class PropostaSemIntegracao {

    @Autowired
    private PropostaRepository propostaRepository;

    @Autowired
    private NotificacaoService notificacaoService;

    @Value("${rabbitmq.propostapendente.exchange}")
    private String exchange;

    //agendamento para rodar o metodo a cada 10 segundos
    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
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
