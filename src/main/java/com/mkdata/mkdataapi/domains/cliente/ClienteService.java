package com.mkdata.mkdataapi.domains.cliente;

import com.mkdata.mkdataapi.domains.cliente.assembler.ClienteAssembler;
import com.mkdata.mkdataapi.domains.cliente.dto.request.ClienteRequestDTO;
import com.mkdata.mkdataapi.domains.cliente.dto.response.ClienteResponseDTO;
import com.mkdata.mkdataapi.domains.cliente.enums.StatusCliente;
import com.mkdata.mkdataapi.exception_handler.exceptions.DuplicatedCnpjOrRgException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteAssembler clienteAssembler;
    private static final String CPF_OR_CNPJ_ALREADY_EXISTS = "CPF ou CNPJ já cadastrados no sistema.";

    public Page<ClienteResponseDTO> findByPageable(String name, StatusCliente customerStatus, Pageable filterPageable) {
        Page<ClienteResponseDTO> byPageable = clienteRepository.findByPageable(name, customerStatus, filterPageable);
        return byPageable;
    }

    public Cliente findById(UUID id) {
        return clienteRepository.findById(id);
    }

    public Cliente create(ClienteRequestDTO clienteRequestDTO) {
        hasDuplicatedCpfOrCnpj(clienteRequestDTO);
        Cliente cliente = clienteAssembler.toEntity(clienteRequestDTO);
        return clienteRepository.save(cliente);
    }

    public Cliente update(ClienteRequestDTO clienteRequestDTO, UUID id) {
        Cliente cliente = clienteRepository.findById(id);
        Cliente clienteEntity = clienteAssembler.toEntity(clienteRequestDTO);
        cliente.update(clienteEntity);
        return clienteRepository.save(clienteEntity);
    }

    public void deleteById(UUID id) {
        Cliente customerById = clienteRepository.findById(id);
        customerById.delete();
        clienteRepository.save(customerById);
    }

    private void hasDuplicatedCpfOrCnpj(ClienteRequestDTO clienteRequestDTO) {
        clienteRepository.findByNumeroDocumento(clienteRequestDTO.getDocumentNumber())
                .ifPresent((cliente) -> {
                    throw new DuplicatedCnpjOrRgException(CPF_OR_CNPJ_ALREADY_EXISTS);
                });
    }

}
