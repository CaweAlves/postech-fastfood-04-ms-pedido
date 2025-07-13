CREATE TABLE cliente
(
    id           SERIAL PRIMARY KEY,
    nome         VARCHAR(100) NOT NULL,
    email        VARCHAR(150),
    cpf          VARCHAR(11) UNIQUE
);