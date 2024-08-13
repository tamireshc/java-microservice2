package com.example.analiseDeCredito.service.strategy.impl;

import com.example.analiseDeCredito.domain.Proposta;
import com.example.analiseDeCredito.exceptions.StrategyException;
import com.example.analiseDeCredito.service.strategy.CalculoPonto;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Random;

@Order(1)
@Component
public class NomeNegativadoImpl implements CalculoPonto {

    @Override
    public int calcular(Proposta proposta) {
        if(nomeNegativado()){
            throw new StrategyException("Nome Negativado");
        }
        return 100;
    }

    private boolean nomeNegativado() {
        return new Random().nextBoolean();
    }
}
