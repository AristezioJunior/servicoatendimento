package br.com.creedev.api.dto.mapper;

import org.springframework.stereotype.Component;

import br.com.creedev.api.dto.request.EnderecoRequest;
import br.com.creedev.api.dto.response.CidadeResponse;
import br.com.creedev.api.dto.response.EnderecoResponse;
import br.com.creedev.api.dto.response.EstadoResponse;
import br.com.creedev.domain.model.Cidade;
import br.com.creedev.domain.model.Endereco;
import br.com.creedev.domain.model.Estado;

@Component
public class EnderecoMapper {

    public Endereco toEntity(EnderecoRequest request, Cidade cidade) {
        if (request == null) return null;
        Endereco endereco = new Endereco();
        endereco.setCep(request.getCep());
        endereco.setLogradouro(request.getLogradouro());
        endereco.setNumero(request.getNumero());
        endereco.setComplemento(request.getComplemento());
        endereco.setBairro(request.getBairro());
        endereco.setCidade(cidade);
        return endereco;
    }

    public EnderecoResponse toResponse(Endereco endereco) {
        if (endereco == null) return null;

        Cidade cidade = endereco.getCidade();
        Estado estado = (cidade != null) ? cidade.getEstado() : null;

        EstadoResponse estadoResp = null;
        if (estado != null) {
            estadoResp = EstadoResponse.builder()
                    .id(estado.getId())
                    .nome(estado.getNome())
                    .build();
        }

        CidadeResponse cidadeResp = null;
        if (cidade != null) {
            cidadeResp = CidadeResponse.builder()
                    .id(cidade.getId())
                    .nome(cidade.getNome())
                    .estado(estadoResp)
                    .build();
        }

        return EnderecoResponse.builder()
                .cep(endereco.getCep())
                .logradouro(endereco.getLogradouro())
                .numero(endereco.getNumero())
                .complemento(endereco.getComplemento())
                .bairro(endereco.getBairro())
                .cidade(cidadeResp)
                .build();
    }
    
    public void updateEntityFromRequest(EnderecoRequest request, Endereco existente, Cidade cidade) {
        if (request == null || existente == null) return;

        existente.setCep(request.getCep());
        existente.setLogradouro(request.getLogradouro());
        existente.setNumero(request.getNumero());
        existente.setComplemento(request.getComplemento());
        existente.setBairro(request.getBairro());
        existente.setCidade(cidade);
    }
}