CREATE TYPE tipo_pessoa_enum AS ENUM('PESSOA_FISICA', 'PESSOA_JURIDICA');
CREATE TYPE status_cliente_enum AS ENUM('ATIVO', 'INATIVO');

create table tb_cliente
(
    id               UUID primary key,
    name              varchar(100),
    person_type       varchar,
    document_number   varchar,
    register_number   varchar,
    telephone_numbers varchar,
    customer_status   varchar,
    created_at        timestamp,
    updated_at        timestamp,
    deleted_at        timestamp
);

create table tb_telefone
(
    id               UUID primary key,
    ddd              varchar,
    number           varchar,
    cliente_id       UUID,
    FOREIGN KEY (cliente_id) REFERENCES tb_cliente(id)
);


