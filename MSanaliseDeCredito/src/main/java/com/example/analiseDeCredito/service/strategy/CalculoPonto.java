package com.example.analiseDeCredito.service.strategy;

import com.example.analiseDeCredito.domain.Proposta;

public interface CalculoPonto {
    int calcular(Proposta proposta);
}
