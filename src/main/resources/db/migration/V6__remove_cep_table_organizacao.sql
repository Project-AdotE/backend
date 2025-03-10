ALTER TABLE organizacao
    ADD COLUMN endereco_id BIGINT,
    ADD CONSTRAINT fk_endereco FOREIGN KEY (endereco_id) REFERENCES endereco_organizacao(id);