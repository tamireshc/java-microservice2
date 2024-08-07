package com.example.Notificacao.listener;

import com.example.Notificacao.constants.MensagemConstante;
import com.example.Notificacao.domain.Proposta;
import com.example.Notificacao.service.NotificacaoSNSService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PropostaPendenteListener {

    @Autowired
    private NotificacaoSNSService notificacaoSNSService;

    //Listener(ouvinte) que irá receber a mensagem da fila proposta.pendente
    @RabbitListener(queues = "${rabbitmq.queue.proposta.pendente}")
    public void propostaPendente(Proposta proposta) {
        String mensagem = String.format(MensagemConstante.PROPOSTA_EM_ANALISE, proposta.getUsuario().getNome());
        notificacaoSNSService.notificar(proposta.getUsuario().getTelefone(), mensagem);
    }
}
