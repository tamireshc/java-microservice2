package com.example.microservico.mapper;

import com.example.microservico.dto.PropostaRequestDTO;
import com.example.microservico.entity.Proposta;
import org.mapstruct.Mapper;

@Mapper
public interface PropostaMapper {
    Proposta convertDTOToProposta(PropostaRequestDTO propostaRequestDTO);
}
