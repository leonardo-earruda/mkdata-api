package com.mkdata.mkdataapi.domains.cliente.dto.request;

import com.mkdata.mkdataapi.annotations.IE;
import com.mkdata.mkdataapi.annotations.RG;
import com.mkdata.mkdataapi.domains.cliente.enums.TipoPessoa;
import com.mkdata.mkdataapi.domains.telefone.dto.TelefoneDTO;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequestDTO {

    @NotNull
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoPessoa personType;

    @CPF(groups = ClienteRequestDTO.PessoaFisica.class)
    @CNPJ(groups = ClienteRequestDTO.PessoaJuridica.class)
    private String documentNumber;

    @IE(groups = ClienteRequestDTO.PessoaJuridica.class)
    @RG(groups = ClienteRequestDTO.PessoaFisica.class)
    private String registerNumber;

    private List<TelefoneDTO> telephoneNumbers;

    public Object getInterface() {
        return TipoPessoa.PESSOA_JURIDICA.equals(personType) ?
                PessoaJuridica.class
                : PessoaFisica.class;
    }

    public interface PessoaJuridica {
    }

    public interface PessoaFisica {
    }

}
