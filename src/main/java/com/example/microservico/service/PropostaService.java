package com.example.microservico.service;

import com.example.microservico.dto.PropostaRequestDTO;
import com.example.microservico.dto.PropostaResponseDTO;
import com.example.microservico.entity.Proposta;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.PropostaRepository;

@Service
public class PropostaService {

    private PropostaRepository propostaRepository;

    public PropostaResponseDTO criar(PropostaRequestDTO propostaRequestDTO){
        propostaRepository.save(new Proposta());
        return null;
    }
}
