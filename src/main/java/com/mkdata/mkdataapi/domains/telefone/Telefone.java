package com.mkdata.mkdataapi.domains.telefone;

import com.mkdata.mkdataapi.domains.cliente.Cliente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_telefone")
public class Telefone {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String number;

    private String ddd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    public Telefone(String number, String ddd, Cliente cliente) {
        this.number = number;
        this.ddd = ddd;
        this.cliente = cliente;
    }

}