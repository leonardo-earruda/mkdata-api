package com.mkdata.mkdataapi.domains.cliente;

import com.mkdata.mkdataapi.domains.cliente.dto.response.ClienteResponseDTO;
import com.mkdata.mkdataapi.domains.cliente.enums.StatusCliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface ClienteRepository {

    Cliente save(Cliente cliente);

    Cliente findById(UUID id);

    Page<ClienteResponseDTO> findByPageable(String name, StatusCliente customerStatus, Pageable pageable);

    Optional<Cliente> findByNumeroDocumento(String numeroDocumento);
}
