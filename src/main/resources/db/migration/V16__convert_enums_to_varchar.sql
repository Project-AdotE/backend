-- 1. Adiciona colunas temporárias VARCHAR
ALTER TABLE animal ADD COLUMN tipo_temp VARCHAR(20);
ALTER TABLE animal ADD COLUMN sexo_temp VARCHAR(20);
ALTER TABLE animal ADD COLUMN porte_temp VARCHAR(20);
ALTER TABLE animal ADD COLUMN idade_temp VARCHAR(20);

-- 2. Copia valores das colunas ENUM para as temporárias
UPDATE animal SET tipo_temp = tipo::VARCHAR;
UPDATE animal SET sexo_temp = sexo::VARCHAR;
UPDATE animal SET porte_temp = porte::VARCHAR;
UPDATE animal SET idade_temp = idade::VARCHAR;

-- 3. Remove as colunas ENUM originais
ALTER TABLE animal DROP COLUMN tipo;
ALTER TABLE animal DROP COLUMN sexo;
ALTER TABLE animal DROP COLUMN porte;
ALTER TABLE animal DROP COLUMN idade;

-- 4. Renomeia colunas temporárias para os nomes originais
ALTER TABLE animal RENAME COLUMN tipo_temp TO tipo;
ALTER TABLE animal RENAME COLUMN sexo_temp TO sexo;
ALTER TABLE animal RENAME COLUMN porte_temp TO porte;
ALTER TABLE animal RENAME COLUMN idade_temp TO idade;

-- 5. Define as colunas como NOT NULL (se aplicável)
ALTER TABLE animal ALTER COLUMN tipo SET NOT NULL;
ALTER TABLE animal ALTER COLUMN sexo SET NOT NULL;
ALTER TABLE animal ALTER COLUMN porte SET NOT NULL;
ALTER TABLE animal ALTER COLUMN idade SET NOT NULL;

-- 6. Adiciona as constraints com valores permitidos (CHECK)
ALTER TABLE animal ADD CONSTRAINT check_tipo_animal CHECK (tipo IN ('CACHORRO', 'GATO'));
ALTER TABLE animal ADD CONSTRAINT check_sexo_animal CHECK (sexo IN ('MACHO', 'FEMEA'));
ALTER TABLE animal ADD CONSTRAINT check_porte_animal CHECK (porte IN ('PEQUENO', 'MEDIO', 'GRANDE'));
ALTER TABLE animal ADD CONSTRAINT check_idade_animal CHECK (idade IN ('FILHOTE', 'JOVEM', 'ADULTO', 'IDOSO'));

-- 7. (Opcional) Remover os tipos ENUM do banco se não forem mais usados
DROP TYPE tipo_animal;
DROP TYPE sexo_animal;
DROP TYPE porte_animal;
DROP TYPE idade_animal;
