package com.example.microservico.controller;

import com.example.microservico.dto.PropostaRequestDTO;
import com.example.microservico.dto.PropostaResponseDTO;
import com.example.microservico.entity.Proposta;
import com.example.microservico.service.PropostaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@AllArgsConstructor
@RestController

@RequestMapping("/proposta")
public class PropostaController {

    @Autowired
    private PropostaService propostaService;

    @PostMapping
    public ResponseEntity<PropostaResponseDTO> criar(@RequestBody PropostaRequestDTO requestDTO) {
        PropostaResponseDTO response = propostaService.criar(requestDTO);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(response.getId())
                        .toUri())
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<Proposta>> obterPropostas() {
        return ResponseEntity.ok(propostaService.obterPropostas());
    }
}
