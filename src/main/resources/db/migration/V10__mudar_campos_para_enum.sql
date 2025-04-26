CREATE TYPE sexo_animal AS ENUM ('MACHO', 'FEMEA');
CREATE TYPE porte_animal AS ENUM ('PEQUENO', 'MEDIO', 'GRANDE');
CREATE TYPE idade_animal AS ENUM ('FILHOTE', 'JOVEM', 'ADULTO', 'IDOSO');
CREATE TYPE tipo_animal AS ENUM ('CACHORRO', 'GATO');

ALTER TABLE animal
ALTER COLUMN sexo TYPE sexo_animal USING sexo::sexo_animal,
    ALTER COLUMN porte TYPE porte_animal USING porte::porte_animal,
    ALTER COLUMN idade TYPE idade_animal USING idade::idade_animal,
    ALTER COLUMN tipo TYPE tipo_animal USING tipo::tipo_animal;