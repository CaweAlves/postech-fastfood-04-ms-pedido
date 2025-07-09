#!/bin/bash

#############################################
### Shellscript to up Docker's containers ###
#############################################

rm -rf target/
mvn clean install -DskipTests

### Docker ###
cd run

echo -e "\n\n"
echo -e "\033[01;32m#######################################\033[01;32m"
echo -e "\033[01;32m### Fazendo o build das imagens.... ###\033[01;32m"
echo -e "\033[01;32m#######################################\033[01;32m"
echo -e "\n"

sudo docker-compose build

echo -e "\n\n"
echo -e "\033[01;32m#############################\033[01;32m"
echo -e "\033[01;32m### Subindo os contianers ###\033[01;32m"
echo -e "\033[01;32m#############################\033[01;32m"
echo -e "\n"

sudo docker-compose up -d

echo -e "\n"
echo -e "\033[01;32m############################\033[01;32m"
echo -e "\033[01;32m### Aplicação Rodando!!! ###\033[01;32m"
echo -e "\033[01;32m############################\033[01;32m"