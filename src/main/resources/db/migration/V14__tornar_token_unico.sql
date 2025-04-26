ALTER TABLE password_token
    ADD CONSTRAINT unique_token UNIQUE (token);