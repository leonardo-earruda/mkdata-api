package com.mkdata.mkdataapi.domains.cliente;

import com.mkdata.mkdataapi.domains.cliente.dto.response.ClienteResponseDTO;
import com.mkdata.mkdataapi.domains.cliente.enums.StatusCliente;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class ClienteRepositoryImpl implements ClienteRepository {

    private final ClienteRepositoryJpa clienteRepositoryJpa;

    @Override
    public Cliente save(Cliente cliente) {
        return clienteRepositoryJpa.save(cliente);
    }

    @Override
    public Cliente findById(UUID id) {
        return clienteRepositoryJpa.findById(id).orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado!"));
    }

    @Override
    public Page<ClienteResponseDTO> findByPageable(String name, StatusCliente customerStatus, Pageable pageable) {
        return clienteRepositoryJpa.findByPageable(name, customerStatus, pageable);
    }

    @Override
    public Optional<Cliente> findByNumeroDocumento(String numeroDocumento) {
        return clienteRepositoryJpa.findByDocumentNumber(numeroDocumento);
    }

}
