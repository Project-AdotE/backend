ALTER TABLE perguntas
    ADD CONSTRAINT chk_tipo_pergunta CHECK (tipo IN ('TEXTO', 'ALTERNATIVA'));