package com.mkdata.mkdataapi.domains.cliente.dto.request;

import com.mkdata.mkdataapi.annotations.IE;
import com.mkdata.mkdataapi.annotations.RG;
import com.mkdata.mkdataapi.domains.cliente.Cliente;
import com.mkdata.mkdataapi.domains.cliente.enums.TipoPessoa;
import com.mkdata.mkdataapi.domains.telefone.Telefone;
import com.mkdata.mkdataapi.domains.telefone.dto.TelefoneDTO;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequestDTO {

    @NotNull
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoPessoa personType;

    @CPF(groups = ClienteRequestDTO.PessoaFisica.class)
    @CNPJ(groups = ClienteRequestDTO.PessoaJuridica.class)
    private String documentNumber;

    @IE(groups = ClienteRequestDTO.PessoaJuridica.class)
    @RG(groups = ClienteRequestDTO.PessoaFisica.class)
    private String registerNumber;

    private Set<TelefoneDTO> telephoneNumbers;

    public Object getInterface() {
        return TipoPessoa.PESSOA_JURIDICA.equals(personType) ?
                PessoaJuridica.class
                : PessoaFisica.class;
    }

    public interface PessoaJuridica {
    }

    public interface PessoaFisica {
    }

    public Cliente updateEntity(Cliente customer) {
        customer.setName(this.name);
        customer.setPersonType(this.personType);
        customer.setDocumentNumber(this.documentNumber);
        customer.setRegisterNumber(this.registerNumber);

        Map<UUID, Telefone> telephonesById = customer.getTelephoneNumbers().stream()
                .collect(Collectors.toMap(Telefone::getId, Function.identity()));

        List<Telefone> updatedTelephones = this.telephoneNumbers.stream()
                .map(dto -> {
                    Telefone telephone;
                    if (dto.getId() != null) {
                        telephone = telephonesById.get(dto.getId());
                        dto.updateEntity(telephone);
                    } else {
                        telephone = dto.toEntity();
                        telephone.setCliente(customer);
                    }
                    return telephone;
                }).toList();

        customer.getTelephoneNumbers().clear();
        customer.getTelephoneNumbers().addAll(updatedTelephones);
        return customer;
    }

}
