package com.mkdata.mkdataapi.domains.telefone.assembler;

import com.mkdata.mkdataapi.domains.cliente.Cliente;
import com.mkdata.mkdataapi.domains.telefone.Telefone;
import com.mkdata.mkdataapi.domains.telefone.dto.TelefoneDTO;

import java.util.Set;
import java.util.stream.Collectors;

public class TelefoneAssembler {

    public static Set<Telefone> toEntity(Set<TelefoneDTO> telefones, Cliente cliente) {
        return telefones
                .stream()
                .map(telefone -> new Telefone(telefone.getNumber(), telefone.getDdd(), cliente))
                .collect(Collectors.toSet());
    }

    public static Set<TelefoneDTO> toResponseModel(Set<Telefone> telefones) {
        return telefones
                .stream()
                .map(telefone -> new TelefoneDTO(telefone.getNumber(), telefone.getDdd()))
                .collect(Collectors.toSet());
    }

}
