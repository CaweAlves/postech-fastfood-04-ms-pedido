CREATE TABLE pedido_produto
(
    id           SERIAL PRIMARY KEY,
    pedido_id  INT REFERENCES pedido (id) ON DELETE CASCADE,
    produto_id INT REFERENCES produto (id) ON DELETE CASCADE,
    quantidade INT NOT NULL DEFAULT 1
);