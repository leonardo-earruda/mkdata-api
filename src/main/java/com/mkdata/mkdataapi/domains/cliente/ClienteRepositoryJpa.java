package com.mkdata.mkdataapi.domains.cliente;

import com.mkdata.mkdataapi.domains.cliente.dto.response.ClienteResponseDTO;
import com.mkdata.mkdataapi.domains.cliente.enums.StatusCliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ClienteRepositoryJpa extends JpaRepository<Cliente, UUID> {

    Optional<Cliente> findByDocumentNumber(String numeroDocumento);

    @Query("SELECT DISTINCT new com.mkdata.mkdataapi.domains.cliente.dto.response.ClienteResponseDTO(cliente) " +
            "FROM Cliente cliente " +
            "LEFT JOIN cliente.telephoneNumbers telephones " +
            "WHERE (:name = '' OR UPPER(cliente.name) LIKE %:name%) " +
            "AND (:customerStatus IS NULL OR cliente.customerStatus = :customerStatus) ")
    Page<ClienteResponseDTO> findByPageable(
            @Param("name") String name,
            @Param("customerStatus") StatusCliente customerStatus,
            Pageable pageable
    );
}
