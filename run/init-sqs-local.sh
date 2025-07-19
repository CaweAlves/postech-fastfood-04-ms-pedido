#!/bin/sh
echo "### Executando script de inicialização do SQS ###"

echo "Criando a fila para receber confirmações de pagamento: fila-pedidos..."
awslocal sqs create-queue --queue-name fila-pedidos

# Adicionado: Cria a fila onde o serviço de pagamentos publica os resultados
echo "Criando a fila para publicar resultados de pagamento: fila-pagamentos..."
awslocal sqs create-queue --queue-name fila-pagamentos

echo "Listando as filas SQS para verificação:"
awslocal sqs list-queues

echo "### Script de inicialização do SQS concluído ###"