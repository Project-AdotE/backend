CREATE TABLE organizacao (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    numero VARCHAR(20) NOT NULL,
    cnpj VARCHAR(20) NOT NULL UNIQUE,
    cep VARCHAR(10) NOT NULL,
    email VARCHAR(255) NOT NULL,
    senha VARCHAR(255) NOT NULL
);

CREATE TABLE animal (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    sexo VARCHAR(10) NOT NULL CHECK (sexo IN ('MACHO', 'FEMEA')),
    porte VARCHAR(10) NOT NULL CHECK (porte IN ('PEQUENO', 'MEDIO', 'GRANDE')),
    vacinado BOOLEAN NOT NULL,
    url_foto VARCHAR(255),
    organizacao_id BIGINT NOT NULL,
    FOREIGN KEY (organizacao_id) REFERENCES organizacao(id) ON DELETE CASCADE
);