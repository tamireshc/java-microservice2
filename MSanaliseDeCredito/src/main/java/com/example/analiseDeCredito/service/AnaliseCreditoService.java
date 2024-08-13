package com.example.analiseDeCredito.service;

import com.example.analiseDeCredito.domain.Proposta;
import com.example.analiseDeCredito.exceptions.StrategyException;
import com.example.analiseDeCredito.service.strategy.CalculoPonto;
import org.springframework.objenesis.SpringObjenesis;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnaliseCreditoService {

    private List<CalculoPonto> calculoPontoList;

    //Como as implementacoes foram marcadas com um @Bean(@Component) a Lista enxerga todas as implementacoes
    public AnaliseCreditoService(List<CalculoPonto> calculoPontoList) {
        this.calculoPontoList = calculoPontoList;
    }

    //Aqui é feito o calculo da pontuacao da proposta que itera sobre cada item da lista de implementacoes de calculo ponto
    public void analizar(Proposta proposta) {
        try {
            boolean aprovada = calculoPontoList.stream()
                    .mapToInt(imp -> imp.calcular(proposta)).sum() > 350;
            proposta.setAprovada(aprovada);
        } catch (StrategyException e) {
            
        }
    }
}
