#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "postgres" <<-EOSQL
    -- Cria o banco de dados para o microsserviço de Produtos
    CREATE DATABASE "$POSTGRES_DB_PRODUTOS";
EOSQL