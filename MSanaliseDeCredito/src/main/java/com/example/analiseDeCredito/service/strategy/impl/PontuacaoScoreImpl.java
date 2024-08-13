package com.example.analiseDeCredito.service.strategy.impl;

import com.example.analiseDeCredito.domain.Proposta;
import com.example.analiseDeCredito.exceptions.StrategyException;
import com.example.analiseDeCredito.service.strategy.CalculoPonto;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Random;

@Order(2)
@Component
public class PontuacaoScoreImpl implements CalculoPonto {

    @Override
    public int calcular(Proposta proposta) {
        int score = score();
        if (score <= 200) {
            throw new StrategyException("Score Baixo");
        } else if (score <= 400) {
            return 150;
        } else if (score <= 600) {
            return 180;
        } else {
            return 220;
        }
    }

    private int score() {
        return new Random().nextInt(0, 1000);
    }
}
