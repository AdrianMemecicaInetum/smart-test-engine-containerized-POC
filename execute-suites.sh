#!/bin/bash
set -e  # Se aborta si hay algun error

# Carga el .evn si existe
if [ -f .env ]; then
    # Exporta las variables del .env
    export $(grep -v '^#' .env | xargs)
fi

# Variables configurables
IMAGE_NAME="adrianinetum2/smarttestengine:v1"
CONTAINER_NAME="execution-container"
GITHUB_REPO_URL="github.com/PabloCastillo-Inetum/DevOps_Docker.git"
#Configura tu ruta 
CURRENT_DIR="/c/Users/adrian.memecica/Desktop/smart-test-engine-containerized-POC/v2.0/smart-test-engine-containerized-POC"

# asegura que el token de acceso a github este definido
if [ -z "$PAT" ]; then
    echo "Error: PAT is not set. Exiting."
    exit 1
fi

# Clone the repository on the host
echo "Cloning repository..."
git clone https://${PAT}@${GITHUB_REPO_URL} repo



# Maquina nueva
podman machine rm -f
podman machine init
podman machine start

# Se ejecuta el contenedor y se montan los volumenes, los tests suites que se quieren ejecutar se pueden modificar en el parametro -Dtest
echo "Running container..."
podman run --name "$CONTAINER_NAME" \
    --mount "type=bind,source=$CURRENT_DIR/repo/Tests,target=/app/src/test/java/web/TestSuites" \
    --mount "type=bind,source=$CURRENT_DIR/repo/Reports,target=/app/resume-report" \
    "$IMAGE_NAME" mvn clean test -Dtest=TESTWEB


# esperamos que acabe el contenedor
echo "Waiting for the container to finish..."
podman wait "$CONTAINER_NAME"


# se hace el comit de los reportes, desde la carpeta report
echo "Committing reports..."
cd repo
git add .
git commit -m "Automated commit - $(date)"
git push 
cd ..

#borramos el repositorio para que no se haga commits innecesarios y el script se pueda reutilizar
rm -rf repo

echo "Process completed successfully."
