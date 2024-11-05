# entrypoint.sh
#!/bin/bash

# Ejecutar el comando Maven
podman run  -d --name "$CONTAINER_NAME" "$IMAGE_NAME"
mvn clean test

# Verificar que la operación anterior haya sido exitosa
if [ $? -eq 0 ]; then
    # Hacer copia de archivos después de los tests
    cp <origen> <destino>

    # Añadir cambios al repositorio Git
    git add .

    # Realizar un commit con un mensaje específico
    git commit -m "Resultado de ejecución de tests"

    # Hacer push de los cambios al repositorio remoto
    git push origin <nombre-branch>
else
    echo "Error en mvn clean test. No se ejecutarán los siguientes pasos."
    exit 1
fi
