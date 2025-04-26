CREATE TABLE formularios (
    id BIGSERIAL PRIMARY KEY,
    id_animal BIGINT NOT NULL,
    id_org BIGINT NOT NULL,
    nome_adotante VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    telefone VARCHAR(255) NOT NULL,
    cpf VARCHAR(255) NOT NULL,
    status VARCHAR(255) NOT NULL CHECK (status IN ('PENDENTE', 'APROVADO', 'RECUSADO')),
    data_envio TIMESTAMP,
    data_resposta TIMESTAMP,
    FOREIGN KEY (id_animal) REFERENCES animal(id) ON DELETE CASCADE,
    FOREIGN KEY (id_org) REFERENCES organizacao(id) ON DELETE CASCADE
);

CREATE TABLE perguntas (
    id BIGSERIAL PRIMARY KEY,
    pergunta VARCHAR(255) NOT NULL UNIQUE,
    posicao INTEGER NOT NULL UNIQUE,
    tipo VARCHAR(255) NOT NULL
);

CREATE TABLE respostas (
    id BIGSERIAL PRIMARY KEY,
    id_formulario BIGINT NOT NULL,
    id_pergunta BIGINT NOT NULL,
    resposta VARCHAR(255) NOT NULL,
    FOREIGN KEY (id_formulario) REFERENCES formularios(id) ON DELETE CASCADE,
    FOREIGN KEY (id_pergunta) REFERENCES perguntas(id) ON DELETE CASCADE
);