package br.com.creedev.api.dto.mapper;

import org.springframework.stereotype.Component;

import br.com.creedev.api.dto.request.PetRequest;
import br.com.creedev.api.dto.response.PetResponse;
import br.com.creedev.domain.model.Cliente;
import br.com.creedev.domain.model.Especie;
import br.com.creedev.domain.model.Pet;
import br.com.creedev.domain.model.Raca;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PetMapper {

    private final EspecieMapper especieMapper;
    private final RacaMapper racaMapper;
    private final ClienteMapper clienteMapper; // Para mapear o dono

    public PetResponse toResponse(Pet pet) {
        if (pet == null) return null;

        return PetResponse.builder()
                .id(pet.getId())
                .nome(pet.getNome())
                .especie(especieMapper.toResponse(pet.getEspecie()))
                .raca(racaMapper.toResponse(pet.getRaca()))
                .sexo(pet.getSexo())
                .peso(pet.getPeso())
                .dataNascimento(pet.getDataNascimento())
                .observacoes(pet.getObservacoes())
                .dono(clienteMapper.toResumidoResponse(pet.getDono()))
                .status(pet.getStatus())
                .dataCriacao(pet.getDataCriacao())
                .dataAlteracao(pet.getDataAlteracao())
                .build();
    }

    /**
     * Constrói a entidade Pet a partir do Request e das entidades relacionadas já resolvidas.
     */
    public Pet toEntity(PetRequest request, Especie especie, Raca raca, Cliente dono) {
        if (request == null) return null;

        Pet pet = new Pet();
        pet.setNome(request.getNome());
        pet.setEspecie(especie);
        pet.setRaca(raca);
        pet.setSexo(request.getSexo());
        pet.setPeso(request.getPeso());
        pet.setDataNascimento(request.getDataNascimento());
        pet.setObservacoes(request.getObservacoes());
        pet.setDono(dono);
        return pet;
    }

    /**
     * Atualiza a entidade existente com os dados do request.
     */
    public void updateEntityFromRequest(PetRequest request, Pet existente, Especie especie, Raca raca, Cliente dono) {
        if (request == null || existente == null) return;

        existente.setNome(request.getNome());
        existente.setEspecie(especie);
        existente.setRaca(raca);
        existente.setSexo(request.getSexo());
        existente.setPeso(request.getPeso());
        existente.setDataNascimento(request.getDataNascimento());
        existente.setObservacoes(request.getObservacoes());
        existente.setDono(dono);
    }
}