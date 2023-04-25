package com.mkdata.mkdataapi.domains.cliente;

import com.mkdata.mkdataapi.domains.cliente.dto.request.ClienteRequestDTO;
import com.mkdata.mkdataapi.domains.cliente.enums.StatusCliente;
import com.mkdata.mkdataapi.domains.cliente.enums.TipoPessoa;
import com.mkdata.mkdataapi.domains.telefone.Telefone;
import com.mkdata.mkdataapi.domains.telefone.assembler.TelefoneAssembler;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

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

    public void update(ClienteRequestDTO cliente) {
        this.updatedAt = LocalDateTime.now();
        this.name = cliente.getName();
        this.personType = cliente.getPersonType();
        this.documentNumber = cliente.getDocumentNumber();
        this.registerNumber = cliente.getRegisterNumber();
        this.telephoneNumbers = TelefoneAssembler.toEntity(cliente.getTelephoneNumbers(), this);
    }

}
