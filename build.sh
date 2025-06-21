#!/bin/bash

# Diretórios
SRC_DIR="src"
BIN_DIR="bin"

# Cria o diretório bin se não existir
mkdir -p "$BIN_DIR"

# Encontra e compila todos os arquivos .java dentro de src/
echo "Compilando arquivos .java..."
find "$SRC_DIR" -name "*.java" > sources.txt

# Compila usando a lista de arquivos encontrados
javac -d "$BIN_DIR" @sources.txt

# Remove a lista temporária
rm sources.txt

echo "✅ Compilação concluída com sucesso!"

# Executa o programa
echo "Iniciando o programa:"
java -cp "$BIN_DIR" src.Main