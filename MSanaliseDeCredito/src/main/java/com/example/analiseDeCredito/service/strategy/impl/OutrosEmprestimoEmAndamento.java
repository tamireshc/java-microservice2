package com.example.analiseDeCredito.service.strategy.impl;

import com.example.analiseDeCredito.domain.Proposta;
import com.example.analiseDeCredito.service.strategy.CalculoPonto;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class OutrosEmprestimoEmAndamento implements CalculoPonto {

    @Override
    public int calcular(Proposta proposta) {
        return outrosEmprestimosEmAndamento() ? 0 : 80;
    }

    private boolean outrosEmprestimosEmAndamento() {
        return new Random().nextBoolean();
    }
}
