package com.example.microservico.service;

import com.example.microservico.dto.PropostaRequestDTO;
import com.example.microservico.dto.PropostaResponseDTO;
import com.example.microservico.entity.Proposta;
import com.example.microservico.entity.Usuario;
import com.example.microservico.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.microservico.repository.PropostaRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PropostaService {
    @Autowired
    private PropostaRepository propostaRepository;
    //    @Autowired
//    private UsuarioRepository usuarioRepository;
    @Autowired
    NotificacaoService notificacaoService;

    public PropostaService(PropostaRepository propostaRepository) {
        this.propostaRepository = propostaRepository;
    }

    @Value("${rabbitmq.propostapendente.exchange}")
    private String exchange;

    @Transactional
    public PropostaResponseDTO criar(PropostaRequestDTO propostaRequestDTO) {
        Proposta proposta = new Proposta();
        proposta.setValorSolicitado(propostaRequestDTO.getValorSolicitado());
        proposta.setPrazoPagamento(propostaRequestDTO.getPrazoPagamento());
        proposta.setIntegrada(true);

        Usuario usuario = new Usuario();
        usuario.setNome(propostaRequestDTO.getNome());
        usuario.setSobrenome(propostaRequestDTO.getSobrenome());
        usuario.setCpf(propostaRequestDTO.getCpf());
        usuario.setTelefone(propostaRequestDTO.getTelefone());
        usuario.setRenda(propostaRequestDTO.getRenda());
        proposta.setUsuario(usuario);
//        usuarioRepository.save(usuario);

        Proposta response = propostaRepository.save(proposta);
        notificarRabbitMQ(proposta);
        return converterPropostaParaPropostaResponseDTO(response);
    }

    // resiliencia caso o envio da mensagem para o rabbitMQ falhe
    private void notificarRabbitMQ(Proposta proposta) {
        try {
            notificacaoService.notificar(proposta, exchange);
        } catch (RuntimeException ex) {
            proposta.setIntegrada(false);
            //edicao da proposta para setar o campo integrada como false
            propostaRepository.findById(proposta.getId()).ifPresent(propostaRepository::save);
        }
    }

    public List<PropostaResponseDTO> obterPropostas() {
        List<Proposta> propostas = propostaRepository.findAll();
        List<PropostaResponseDTO> propostaResponseDTOS = new ArrayList<>();

        for (Proposta proposta : propostas) {
            PropostaResponseDTO responseDTO = converterPropostaParaPropostaResponseDTO(proposta);
            propostaResponseDTOS.add(responseDTO);
        }
        return propostaResponseDTOS;
    }

    public PropostaResponseDTO converterPropostaParaPropostaResponseDTO(Proposta proposta) {
        PropostaResponseDTO responseDTO = new PropostaResponseDTO();
        responseDTO.setId(proposta.getId());
        responseDTO.setValorSolicitado(proposta.getValorSolicitado());
        responseDTO.setPrazoPagamento(proposta.getPrazoPagamento());
        responseDTO.setAprovada(proposta.getAprovada());
        responseDTO.setObservacao(proposta.getObservacao());
        responseDTO.setNome(proposta.getUsuario().getNome());
        responseDTO.setSobrenome(proposta.getUsuario().getSobrenome());
        responseDTO.setCpf(proposta.getUsuario().getCpf());
        responseDTO.setTelefone(proposta.getUsuario().getTelefone());
        responseDTO.setRenda(proposta.getUsuario().getRenda());
        return responseDTO;
    }
}
