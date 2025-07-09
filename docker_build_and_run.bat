@echo off
REM #############################################
REM ### Batch script to up Docker's containers ###
REM #############################################

REM Limpar e compilar o projeto Maven sem rodar testes
echo.
echo ========================================
echo == Limpando e compilando o projeto... ==
echo ========================================
echo.

call mvnw.cmd clean install -DskipTests

REM Verificar se a compilação teve sucesso
if %errorlevel% neq 0 (
    echo Erro ao compilar o projeto com Maven. Abortando...
    pause
    exit /b %errorlevel%
)

REM Navegar para o diretório 'run'
cd run

echo.
echo ========================================
echo == Fazendo o build das imagens...     ==
echo ========================================
echo.

docker-compose build

echo.
echo ========================================
echo == Subindo os containers...           ==
echo ========================================
echo.

docker-compose up -d

echo.
echo ========================================
echo == Containers iniciados com sucesso!  ==
echo ========================================
pause