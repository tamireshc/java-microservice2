package com.example.analiseDeCredito.service.strategy.impl;

import com.example.analiseDeCredito.domain.Proposta;
import com.example.analiseDeCredito.service.strategy.CalculoPonto;

public class RendaMaiorValorSolicitado implements CalculoPonto {

    @Override
    public int calcular(Proposta proposta) {
        return rendaMaiorValorSolicitado(proposta) ? 100 : 0;
    }

    private boolean rendaMaiorValorSolicitado(Proposta proposta) {
        return proposta.getUsuario().getRenda() > proposta.getValorSolicitado();
    }
}
