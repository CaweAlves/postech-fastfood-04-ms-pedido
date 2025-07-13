CREATE TABLE categoria
(
    id           SERIAL PRIMARY KEY,
    nome         VARCHAR(50) NOT NULL UNIQUE,
    descricao    TEXT
);

CREATE TABLE produto
(
    id           SERIAL PRIMARY KEY,
    nome         VARCHAR(100)   NOT NULL,
    descricao    TEXT,
    preco        NUMERIC(10, 2) NOT NULL,
    categoria_id INT            NOT NULL REFERENCES categoria (id) ON DELETE RESTRICT ON UPDATE CASCADE
);