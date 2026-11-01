CREATE TABLE produto (
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome        VARCHAR(120) NOT NULL,
    descricao   VARCHAR(500),
    preco       NUMERIC(10,2) NOT NULL CHECK (preco >= 0),
    estoque     INTEGER NOT NULL CHECK (estoque >= 0),
    ativo       BOOLEAN NOT NULL DEFAULT TRUE,
    criado_em   TIMESTAMP NOT NULL DEFAULT now()
);