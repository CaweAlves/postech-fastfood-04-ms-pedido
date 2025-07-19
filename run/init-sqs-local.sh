#!/bin/sh
echo "Aguardando o LocalStack iniciar..."
sleep 10

echo "Criando a fila SQS..."
awslocal sqs create-queue --queue-name fila-pedidos

echo "Listando as filas SQS para verificação..."
awslocal sqs list-queues