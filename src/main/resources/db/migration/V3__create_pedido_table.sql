CREATE TABLE pedido
(
    id               SERIAL PRIMARY KEY,
    cliente_id       INT            REFERENCES cliente (id) ON DELETE SET NULL,
    total            NUMERIC(10, 2) NOT NULL,
    status           VARCHAR(13)
);