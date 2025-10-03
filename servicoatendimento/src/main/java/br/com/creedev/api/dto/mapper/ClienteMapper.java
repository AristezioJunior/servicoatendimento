package br.com.creedev.api.dto.mapper;

import org.springframework.stereotype.Component;

import br.com.creedev.api.dto.request.ClienteRequest;
import br.com.creedev.api.dto.response.ClienteResponse;
import br.com.creedev.api.dto.response.ClienteResumidoResponse;
import br.com.creedev.domain.model.Cliente;
import br.com.creedev.domain.model.Endereco;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ClienteMapper {

    private final EnderecoMapper enderecoMapper;

    public ClienteResponse toResponse(Cliente cliente) {
        if (cliente == null) return null;

        return ClienteResponse.builder()
                .id(cliente.getId())
                .nome(cliente.getNome())
                .cpf(cliente.getCpf())
                .telefoneFixo(cliente.getTelefoneFixo())
                .celular(cliente.getCelular())
                .email(cliente.getEmail())
                .endereco(enderecoMapper.toResponse(cliente.getEndereco()))
                .status(cliente.getStatus())
                .dataCriacao(cliente.getDataCriacao())
                .dataAlteracao(cliente.getDataAlteracao())
                .build();
    }

    public Cliente toEntity(ClienteRequest request, Endereco endereco) {
        if (request == null) return null;

        Cliente cliente = new Cliente();
        cliente.setNome(request.getNome());
        cliente.setCpf(request.getCpf());
        cliente.setTelefoneFixo(request.getTelefoneFixo());
        cliente.setCelular(request.getCelular());
        cliente.setEmail(request.getEmail());
        cliente.setEndereco(endereco); // já resolvido no service (com Cidade carregada)
        return cliente;
    }

    public void updateEntityFromRequest(ClienteRequest request, Cliente existente, Endereco endereco) {
        if (request == null || existente == null) return;

        existente.setNome(request.getNome());
        existente.setCpf(request.getCpf());
        existente.setTelefoneFixo(request.getTelefoneFixo());
        existente.setCelular(request.getCelular());
        existente.setEmail(request.getEmail());
        existente.setEndereco(endereco); // atualizado se informado
    }

	public ClienteResumidoResponse toResumidoResponse(Cliente dono) {
		if (dono == null) return null;

        return ClienteResumidoResponse.builder()
                .id(dono.getId())
                .nome(dono.getNome())
                .build();
	}
}