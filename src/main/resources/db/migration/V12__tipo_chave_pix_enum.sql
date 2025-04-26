CREATE TYPE tipo_chave_pix AS ENUM ('CPF', 'CNPJ', 'EMAIL', 'TELEFONE', 'ALEATORIA');

ALTER TABLE chaves_pix
ALTER COLUMN tipo TYPE tipo_chave_pix USING tipo::tipo_chave_pix;