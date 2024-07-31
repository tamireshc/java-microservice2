package com.example.microservico.service;

import com.example.microservico.dto.PropostaRequestDTO;
import com.example.microservico.dto.PropostaResponseDTO;
import com.example.microservico.entity.Proposta;
import com.example.microservico.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.PropostaRepository;

@Service
public class PropostaService {
    @Autowired
    private PropostaRepository propostaRepository;

    public PropostaService(PropostaRepository propostaRepository) {
        this.propostaRepository = propostaRepository;
    }

    @Transactional
    public PropostaResponseDTO criar(PropostaRequestDTO propostaRequestDTO){
        Proposta proposta = new Proposta();
        proposta.setValorSolicitado(propostaRequestDTO.getValorSolicitado());
        proposta.setPrazoPagamento(propostaRequestDTO.getPrazoPagamento());

        Usuario usuario = new Usuario();
        usuario.setNome(propostaRequestDTO.getNome());
        usuario.setSobrenome(propostaRequestDTO.getSobrenome());
        usuario.setCpf(propostaRequestDTO.getCpf());
        usuario.setTelefone(propostaRequestDTO.getTelefone());
        usuario.setRenda(propostaRequestDTO.getRenda());

        proposta.setUsuario(usuario);

        Proposta response = propostaRepository.save(proposta);

        PropostaResponseDTO responseDTO= new PropostaResponseDTO();
        responseDTO.setValorSolicitado(response.getValorSolicitado());
        responseDTO.setPrazoPagamento(response.getPrazoPagamento());
        responseDTO.setAprovada(response.getAprovada());
        responseDTO.setObservacao(response.getObservacao());
        responseDTO.setNome(propostaRequestDTO.getNome());
        responseDTO.setSobrenome(propostaRequestDTO.getSobrenome());
        responseDTO.setCpf(propostaRequestDTO.getCpf());
        responseDTO.setTelefone(propostaRequestDTO.getTelefone());
        responseDTO.setRenda(propostaRequestDTO.getRenda());

        return responseDTO;
    }
}
