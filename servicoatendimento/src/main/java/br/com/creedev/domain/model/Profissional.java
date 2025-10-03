package br.com.creedev.domain.model;

import java.util.HashSet;
import java.util.Set;

import br.com.creedev.domain.model.Enums.ProfissionalEspecialidade;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "profissionais", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email", "tenant_id"})
})
public class Profissional extends EntidadeAuditavel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank
    @Size(max = 150)
    @Column(nullable = false, length = 150)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(length = 40, nullable = false)
    private ProfissionalEspecialidade especialidade;

    @Size(max = 15)
    @Column(length = 15)
    private String telefoneFixo;

    @NotBlank
    @Size(max = 15)
    @Column(nullable = false, length = 15)
    private String celular;

    @Email
    @Size(max = 150)
    @Column(length = 150)
    private String email;

    @Embedded
    private Endereco endereco;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "profissionais_servicos",
            joinColumns = @JoinColumn(name = "profissional_id"),
            inverseJoinColumns = @JoinColumn(name = "servico_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"profissional_id", "servico_id"})
    )
    private Set<Servico> servicos = new HashSet<>();

    @Size(max = 500)
    @Column(length = 500)
    private String observacoes;
}