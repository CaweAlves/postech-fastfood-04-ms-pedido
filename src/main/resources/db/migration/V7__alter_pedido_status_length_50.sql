alter table pedido
    alter column status type varchar(50) using status::varchar(50);