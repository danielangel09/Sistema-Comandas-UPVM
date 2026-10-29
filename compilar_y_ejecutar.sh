#!/bin/bash
echo "=========================================="
echo "  Compilando Sistema de Comandas..."
echo "=========================================="

mkdir -p bin

javac -d bin src/structures/*.java src/model/*.java src/controller/*.java src/view/*.java

if [ $? -eq 0 ]; then
    echo ""
    echo "✓ Compilación exitosa"
    echo ""
    echo "=========================================="
    echo "  Ejecutando el sistema..."
    echo "=========================================="
    echo ""
    java -cp bin view.Main
else
    echo ""
    echo "✗ Error de compilación. Revisa los mensajes anteriores."
fi
