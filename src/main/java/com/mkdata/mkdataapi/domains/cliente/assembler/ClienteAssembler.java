package com.mkdata.mkdataapi.domains.cliente.assembler;

import com.mkdata.mkdataapi.domains.cliente.Cliente;
import com.mkdata.mkdataapi.domains.cliente.dto.request.ClienteRequestDTO;
import com.mkdata.mkdataapi.domains.cliente.dto.response.ClienteResponseDTO;
import com.mkdata.mkdataapi.domains.telefone.Telefone;
import com.mkdata.mkdataapi.domains.telefone.assembler.TelefoneAssembler;
import com.mkdata.mkdataapi.domains.telefone.dto.TelefoneDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ClienteAssembler {

    private final TelefoneAssembler telefoneAssembler;

    public Cliente toEntity(ClienteRequestDTO request) {
        Cliente cliente = new Cliente(
                request.getName(),
                request.getPersonType(),
                request.getDocumentNumber(),
                request.getRegisterNumber()
        );
        List<Telefone> telefones = telefoneAssembler.toEntity(request.getTelephoneNumbers(), cliente);
        cliente.setTelephoneNumbers(telefones);
        return cliente;
    }

    public ClienteResponseDTO toResponseModel(Cliente cliente) {
        ClienteResponseDTO clienteResponseDTO = new ClienteResponseDTO(cliente);

        List<TelefoneDTO> telefones = telefoneAssembler.toResponseModel(cliente.getTelephoneNumbers());
        clienteResponseDTO.setTelephoneNumbers(telefones);

        return clienteResponseDTO;
    }


}

