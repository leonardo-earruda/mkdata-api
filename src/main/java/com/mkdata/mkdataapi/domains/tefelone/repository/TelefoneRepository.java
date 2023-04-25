package com.mkdata.mkdataapi.domains.tefelone.repository;

import com.mkdata.mkdataapi.domains.telefone.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TelefoneRepository extends JpaRepository<Telefone, UUID> {

    Optional<Telefone> findByNumberAndDddAndClienteId(String number, String ddd, UUID id);

}