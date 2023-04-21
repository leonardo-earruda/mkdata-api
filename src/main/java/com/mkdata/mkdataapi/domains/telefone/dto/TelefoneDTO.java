package com.mkdata.mkdataapi.domains.telefone.dto;

import com.mkdata.mkdataapi.domains.telefone.Telefone;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TelefoneDTO {

    private String number;

    private String ddd;

   public TelefoneDTO(Telefone telefone) {
        this.number = telefone.getNumber();
        this.ddd = telefone.getDdd();
    }

    public TelefoneDTO(String number, String ddd) {
        this.number = number;
        this.ddd = ddd;
    }

}
