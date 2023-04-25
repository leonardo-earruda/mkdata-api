package com.mkdata.mkdataapi.domains.cliente.dto.response;

import com.mkdata.mkdataapi.domains.cliente.Cliente;
import com.mkdata.mkdataapi.domains.cliente.enums.StatusCliente;
import com.mkdata.mkdataapi.domains.cliente.enums.TipoPessoa;
import com.mkdata.mkdataapi.domains.telefone.Telefone;
import com.mkdata.mkdataapi.domains.telefone.dto.TelefoneDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ClienteResponseDTO {

    private UUID id;

    private String name;

    private TipoPessoa personType;

    private String documentNumber;

    private String registerNumber;

    private Set<TelefoneDTO> telephoneNumbers;

    private StatusCliente customerStatus;

    public ClienteResponseDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.name = cliente.getName();
        this.personType = cliente.getPersonType();
        this.documentNumber = cliente.getDocumentNumber();
        this.registerNumber = cliente.getRegisterNumber();
        this.telephoneNumbers = this.getTelephoneNumbers(cliente.getTelephoneNumbers());
        this.customerStatus = cliente.getCustomerStatus();
    }

    public Set<TelefoneDTO> getTelephoneNumbers(Set<Telefone> telefones) {
        return telefones
                .stream()
                .map(telefone -> new TelefoneDTO(telefone))
                .collect(Collectors.toSet());
    }

}
