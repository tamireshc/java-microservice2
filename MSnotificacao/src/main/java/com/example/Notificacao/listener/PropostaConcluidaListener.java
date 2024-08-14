package com.example.Notificacao.listener;

import com.example.Notificacao.constants.MensagemConstante;
import com.example.Notificacao.domain.Proposta;
import com.example.Notificacao.service.NotificacaoSNSService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PropostaConcluidaListener {
    @Autowired
    private NotificacaoSNSService notificacaoSNSService;

    //Listener(ouvinte) que irá receber a mensagem da fila proposta.concluida
    @RabbitListener(queues = "${rabbitmq.queue.proposta.concluida}")
    public void propostaConlcuida(Proposta proposta) {
        if (proposta.getAprovada()) {
            System.out.println("aceita");
            String mensagem = String.format(MensagemConstante.ANALISE_CONCLUIDA_EMPRESTIMO_CONCEDIDO, proposta.getUsuario().getNome(), proposta.getValorSolicitado());
            notificacaoSNSService.notificar(proposta.getUsuario().getTelefone(), mensagem);
        } else {
            System.out.println(proposta.getAprovada());
            System.out.println("recusada");
            String mensagem = String.format(MensagemConstante.ANALISE_CONCLUIDA_EMPRESTIMO_NAO_CONCEDIDO, proposta.getUsuario().getNome(), proposta.getValorSolicitado(), proposta.getObservacao());
            notificacaoSNSService.notificar(proposta.getUsuario().getTelefone(), mensagem);
        }
    }
}


