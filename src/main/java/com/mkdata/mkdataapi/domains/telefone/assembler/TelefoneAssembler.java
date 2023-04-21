package com.mkdata.mkdataapi.domains.telefone.assembler;

import com.mkdata.mkdataapi.domains.cliente.Cliente;
import com.mkdata.mkdataapi.domains.telefone.Telefone;
import com.mkdata.mkdataapi.domains.telefone.dto.TelefoneDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TelefoneAssembler {

    public List<Telefone> toEntity(List<TelefoneDTO> telefones, Cliente cliente) {
        return telefones
                .stream()
                .map(telefone -> new Telefone(telefone.getNumber(), telefone.getDdd(), cliente))
                .collect(Collectors.toList());
    }

    public List<TelefoneDTO> toResponseModel(List<Telefone> telefones) {
        return telefones
                .stream()
                .map(telefone -> new TelefoneDTO(telefone.getNumber(), telefone.getDdd()))
                .collect(Collectors.toList());
    }

}
