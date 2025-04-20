CREATE TABLE password_token (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    token VARCHAR(6) NOT NULL,
    expiration_time TIMESTAMP NOT NULL,
    used BOOLEAN DEFAULT FALSE
);