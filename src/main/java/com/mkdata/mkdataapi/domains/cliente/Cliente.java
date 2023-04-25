package com.mkdata.mkdataapi.domains.cliente;

import com.mkdata.mkdataapi.domains.cliente.dto.request.ClienteRequestDTO;
import com.mkdata.mkdataapi.domains.cliente.enums.StatusCliente;
import com.mkdata.mkdataapi.domains.cliente.enums.TipoPessoa;
import com.mkdata.mkdataapi.domains.telefone.Telefone;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "tb_cliente")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    private String name;

    @Enumerated(EnumType.STRING)
    private TipoPessoa personType;

    private String documentNumber;

    private String registerNumber;

    @Enumerated(EnumType.STRING)
    private StatusCliente customerStatus = StatusCliente.ATIVO;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDateTime deletedAt;

    private LocalDateTime updatedAt;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "cliente")
    private Set<Telefone> telephoneNumbers;

    public Cliente(String name, TipoPessoa personType, String documentNumber, String registerNumber) {
        this.name = name;
        this.personType = personType;
        this.documentNumber = documentNumber;
        this.registerNumber = registerNumber;
    }

    public void delete() {
        this.customerStatus = StatusCliente.INATIVO;
        this.deletedAt = LocalDateTime.now();
    }

    public Cliente updateEntity(ClienteRequestDTO clienteRequestDTO) {
        this.name = clienteRequestDTO.getName();
        this.personType = clienteRequestDTO.getPersonType();
        this.documentNumber = clienteRequestDTO.getDocumentNumber();
        this.registerNumber = clienteRequestDTO.getRegisterNumber();

        Map<UUID, Telefone> telephonesById = this.telephoneNumbers.stream()
                .collect(Collectors.toMap(Telefone::getId, Function.identity()));

        List<Telefone> updatedTelephones = clienteRequestDTO.getTelephoneNumbers().stream()
                .map(dto -> {
                    Telefone telephone;
                    if (dto.getId() == null) {
                        telephone = dto.toEntity();
                        telephone.setCliente(this);
                    } else {
                        telephone = telephonesById.get(dto.getId());
                        dto.updateEntity(telephone);
                    }
                    return telephone;
                }).toList();

        Set<Telefone> updatedTelephonesSet = new HashSet<>(updatedTelephones);
        telephonesById.values().stream()
                .filter(telephone -> !updatedTelephonesSet.contains(telephone))
                .forEach(telephone -> telephone.setCliente(null));

        this.telephoneNumbers.clear();
        this.telephoneNumbers.addAll(updatedTelephones);
        return this;
    }

}
