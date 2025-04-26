-- V1_2023_04_26__Convert_tipo_chave_pix_to_varchar.sql

-- 1. Cria coluna temporária VARCHAR
ALTER TABLE chaves_pix ADD COLUMN tipo_temp VARCHAR(20);

-- 2. Copia valores da coluna enum para a coluna VARCHAR
UPDATE chaves_pix SET tipo_temp = tipo::VARCHAR;

-- 3. Remove a coluna com tipo enum personalizado
ALTER TABLE chaves_pix DROP COLUMN tipo;

-- 4. Renomeia a coluna temporária para o nome original
ALTER TABLE chaves_pix RENAME COLUMN tipo_temp TO tipo;

-- 5. Configura a nova coluna como NOT NULL
ALTER TABLE chaves_pix ALTER COLUMN tipo SET NOT NULL;

-- 6. Adiciona CHECK constraint para validar valores permitidos
ALTER TABLE chaves_pix ADD CONSTRAINT check_tipo_chave_pix
    CHECK (tipo IN ('CPF', 'CNPJ', 'EMAIL', 'TELEFONE', 'OUTRO'));

-- 7. Remover o tipo enum personalizado do banco
-- (Descomente apenas se tiver certeza que não é usado em outros lugares)
DROP TYPE tipo_chave_pix;