CREATE TABLE chaves_pix (
    id BIGSERIAL PRIMARY KEY,
    tipo VARCHAR(255) NOT NULL,
    chave VARCHAR(255) NOT NULL UNIQUE,
    organizacao_id BIGINT NOT NULL,
    FOREIGN KEY (organizacao_id) REFERENCES organizacao(id) ON DELETE CASCADE
);