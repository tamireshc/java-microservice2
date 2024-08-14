package com.example.microservico.config;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    private ConnectionFactory connectionFactory;

    @Value("${rabbitmq.propostapendente.exchange}")
    private String exchangedPropostaPendente;

    @Value("${rabbitmq.propostaconcluida.exchange}")
    private String exchangedPropostaConcluida;


    //o próprio spring cria um @bean do connectionfactory e injeta
    public RabbitMQConfiguration(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    //inicializa as filas descritas no @ben no rabbitMq
    @Bean
    public ApplicationListener<ApplicationReadyEvent> inicializerAdmin(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }

    @Bean
    public RabbitAdmin criarRabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    //cria um template para enviar mensagens que utiliza o jackson2JsonMessageConverter
    //Converte automaticamente objetos Java em JSON quando envia mensagens
    //Converte JSON em objetos Java quando recebe mensagens.
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setConnectionFactory(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    //filas

    //forcando o erro para a fila de dead letter com maxLength(2L)( maximo 2 mensagens por fila)
    @Bean
    public Queue criarFilaPropostaPendenteMsAnaliseCredito() {
        return QueueBuilder.durable("proposta-pendente.ms-analise-credito")
                .maxLength(2L)
                .deadLetterExchange("proposta-pendente-dlq.ex")
                .build();
    }

    @Bean
    public Queue criarFilaPropostaPendenteMsNotificacao() {
        return QueueBuilder.durable("proposta-pendente.ms-notificacao").build();
    }

    @Bean
    public Queue criarFilaPropostaConcluidaMsProposta() {
        return QueueBuilder.durable("proposta-concluida.ms-proposta").build();
    }

    @Bean
    public Queue criarFilaPropostaConcluidaMsNotificacao() {
        return QueueBuilder.durable("proposta-concluida.ms-notificacao").build();
    }

    // fila da dead letter
    @Bean
    public Queue criarFilaPropostaPendenteDLQ() {
        return QueueBuilder.durable("proposta-pendente.dlq").build();
    }

    //exchanges

    @Bean
    public FanoutExchange criarFanoutExchangePropostaPendente() {
        return ExchangeBuilder.fanoutExchange(exchangedPropostaPendente).build();
    }

    @Bean
    public FanoutExchange criarFanoutExchangePropostaConcluida() {
        return ExchangeBuilder.fanoutExchange(exchangedPropostaConcluida).build();
    }

    // exchange da dead letter

    @Bean
    public FanoutExchange deadLetterExhanged() {
        return ExchangeBuilder.fanoutExchange("proposta-pendente-dlq.ex").build();
    }

    //binding do exchanged Pendente para as filas de proposta e notificação

    @Bean
    public Binding criarBindingPropostaPendeteMsAnaliseCredito() {
        return BindingBuilder.bind(criarFilaPropostaPendenteMsAnaliseCredito())
                .to(criarFanoutExchangePropostaPendente());
    }

    @Bean
    public Binding criarBindingPropostaPendenteMsNotificacao() {
        return BindingBuilder.bind(criarFilaPropostaPendenteMsNotificacao())
                .to(criarFanoutExchangePropostaPendente());
    }

    //binding do exchanged Concluida para as filas de proposta e notificação

    @Bean
    public Binding criarBindingPropostaConcluidaMSPropostaApp() {
        return BindingBuilder.bind(criarFilaPropostaConcluidaMsProposta())
                .to(criarFanoutExchangePropostaConcluida());
    }

    @Bean
    public Binding criarBindingPropostaConcluidaMsNotificacao() {
        return BindingBuilder.bind(criarFilaPropostaConcluidaMsNotificacao())
                .to(criarFanoutExchangePropostaConcluida());
    }

    //binding do exchanged dead letter para a fila de proposta peondente dlq
    @Bean
    public Binding criarBindingPropostaPendenteDLQ() {
        return BindingBuilder.bind(criarFilaPropostaPendenteDLQ())
                .to(deadLetterExhanged());
    }
}
