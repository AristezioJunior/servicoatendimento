package br.com.creedev.domain.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.creedev.api.dto.mapper.ClienteMapper;
import br.com.creedev.api.dto.mapper.EnderecoMapper;
import br.com.creedev.api.dto.request.ClienteRequest;
import br.com.creedev.api.dto.response.ClienteResponse;
import br.com.creedev.domain.model.Cidade;
import br.com.creedev.domain.model.Cliente;
import br.com.creedev.domain.model.Endereco;
import br.com.creedev.domain.repository.CidadeRepository;
import br.com.creedev.domain.repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;
    private final EnderecoMapper enderecoMapper;
    private final CidadeRepository cidadeRepository;

    @Transactional
    public ClienteResponse salvar(ClienteRequest request) {

        Cidade cidade = cidadeRepository.findById(request.getEndereco().getCidadeId())
                .orElseThrow(() -> new EntityNotFoundException("Cidade não encontrada: " + request.getEndereco().getCidadeId()));

        Endereco endereco = enderecoMapper.toEntity(request.getEndereco(), cidade);
        
    	
        Cliente cliente = clienteMapper.toEntity(request, endereco);

        Cliente salvo = clienteRepository.save(cliente);
        return clienteMapper.toResponse(salvo);
    }

    @Transactional(readOnly = true)
    public ClienteResponse buscarPorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado: " + id));
        return clienteMapper.toResponse(cliente);
    }
	
	@Transactional(readOnly = true)
	public Page<ClienteResponse> buscarPorNomeOuCpf(String termo, Pageable pageable) {
		return clienteRepository
            .findByNomeContainingIgnoreCaseOrCpfContaining(termo, termo, pageable)
            .map(clienteMapper::toResponse);
	}

    @Transactional(readOnly = true)
    public Page<ClienteResponse> listarTodos(Pageable pageable) {
        return clienteRepository.findAll(pageable).map(clienteMapper::toResponse);
    }

    @Transactional
    public ClienteResponse atualizar(Long id, ClienteRequest request) {
        Cliente existente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado: " + id));

        if (request.getEndereco() != null) {
            var cidade = cidadeRepository.findById(request.getEndereco().getCidadeId())
                    .orElseThrow(() -> new EntityNotFoundException("Cidade não encontrada: " + request.getEndereco().getCidadeId()));
            existente.setEndereco(enderecoMapper.toEntity(request.getEndereco(), cidade));
        }
        
        clienteMapper.updateEntityFromRequest(request, existente, existente.getEndereco());

        Cliente salvo = clienteRepository.save(existente);
        return clienteMapper.toResponse(salvo);
    }

    @Transactional
    public void deletar(Long id) {
        Cliente existente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado: " + id));
        clienteRepository.delete(existente);
    }
}