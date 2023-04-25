package com.mkdata.mkdataapi.domains.telefone.service;

import com.mkdata.mkdataapi.domains.tefelone.repository.TelefoneRepository;
import com.mkdata.mkdataapi.domains.telefone.Telefone;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TelefoneService {

    private final TelefoneRepository telefoneRepository;

    public Telefone saveOrUpdateTelefone(Telefone telefone) {
        return telefoneRepository.findByNumberAndDddAndClienteId(telefone.getNumber(), telefone.getDdd(), telefone.getCliente().getId())
                .map(telefone1 -> {
                    telefone1.update(telefone);
                    return telefoneRepository.save(telefone1);
                })
                .orElse(telefoneRepository.save(telefone));
    }
}