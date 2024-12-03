#!/bin/bash
set -e  # Se aborta si hay algun error
 
# Carga el .evn si existe
if [ -f .env ]; then
    # Exporta las variables del .env
    export $(grep -v '^#' .env | xargs)
fi
 
# Variables configurables
#Nombre de la imagen subida a dockerhub, que contiene el STE
IMAGE_NAME="adrianinetum2/smarttestengine:v1"
#Nombre del contenedor que se ejecutara
CONTAINER_NAME="execution-container"
#Nombre de usuario de Github con acceso al repositorio
USERNAME="PabloCastillo-Inetum"
#Metodo con el que se accederá a las suites y se tratarán los reportes (LOCAL/REMOTE)
TEST_SUITE_ORIGIN="REMOTE"
#Nombre de la suite que se quiere ejecutar
TEST_SUITE_NAME="TESTWEB"
#Accion que indica si se quiere subir el commit del resultado en el repositorio (YES/NO)
COMMIT="YES"
 
# Esta logica controla que el entorno de ejecución esté listo y adecuado para realizar las acciones que deseamos.
# Condicion que valida que el usuario tenga podman instalado.
if ! podman --version &>/dev/null; then # En el caso de que no esté instalado muestra un mensaje
    echo "No tienes Podman instalado. Por favor, instálalo primero."
    exit 1
else # Si está instalado realiza las siguientes comprovaciones:
    if ! podman machine start &>/dev/null; then # Prueba a iniciar la máquina, en el caso de que no la pueda iniciar se validan los siguientes aspectos:
        echo "Creando una nueva máquina para la ejecución..."
        if ! podman machine init &>/dev/null; then # Prueba a crear una nueva máquina en caso de que el usuario tenga podman instalado pero sin una máquina creada
            echo "Maquina actualmente iniciada!" #Si ya dispone de una maquina creada y está iniciada muestra un mensaje
        else  # En el caso que no tenga una máquina, la crea y procede a encenderla
            echo "Máquina creada correctamente!"
            echo "Iniciando la nueva maquina..."
            # Se procede a encender esta nueva máquina y en el caso de que haya sucedido un error muestra un mensaje
            if podman machine start &>/dev/null; then
                echo "Máquina de Podman iniciada correctamente!"
            else
                echo "Error: No se pudo inicializar ni iniciar la máquina de Podman."
                exit 1
            fi
        fi
    else # En el caso que tenga la maquina ya creada y esté apagada la enciende
        echo "Máquina de Podman iniciada correctamente!"
    fi
 
    # Una vez la máquina encendida se procede con la ejecución.
 
    # En el caso de que la ejecución sea con datos en local:
    if [ "$TEST_SUITE_ORIGIN" == "LOCAL" ]; then
        if [ -z "$LOCAL_REPO_ROUTE_TESTS" ]; then # Asegura que la ruta en formato Linux donde se encuentran los tests exista  
            echo "Error: La variable LOCAL_REPO_ROUTE_TESTS no existe en el .env, tienes que indicar la ruta en formato Windows donde se encuentran la suite a ejecutar."
            exit 1
        elif [ -z "$LOCAL_REPO_ROUTE_REPORTS" ]; then  # Asegura que la ruta en formato Linux donde se guardarán los reportes exista
            echo "Error: La variable GITHUB_REPO no existe en el .env, tienes que indicar la ruta en formato Windows donde quieres que se guarden los reportes."
            exit 1
        else  # Una vez todo correcto procede a ejecutar el contenedor, una vez finaliza borra el contenedor para futuras ejecuciones
            echo "Ejecutando el contenedor..."
            podman run --name "$CONTAINER_NAME" \
                --mount "type=bind,source=${LOCAL_REPO_ROUTE_TESTS},target=/app/src/test/java/web/TestSuites" \
                --mount "type=bind,source=${LOCAL_REPO_ROUTE_REPORTS},target=/app/resume-report" \
                "$IMAGE_NAME" mvn clean test -Dtest=$TEST_SUITE_NAME
            podman rm $CONTAINER_NAME
            echo "Ejecución finalizada!"
        fi
 
    # En el caso de que la ejecución sea con datos en remoto:
    elif [ "$TEST_SUITE_ORIGIN" == "REMOTE" ]; then
        if [ -z "$PAT" ]; then     # Asegura que el token de acceso a github esté definido
            echo "Error: La variable PAT no existe en el .env, tienes que indicar el token de acceso para trabajar con el repositorio."
            exit 1
        elif [ -z "$GITHUB_REPO" ]; then    # Asegura que la ruta del repositorio de github esté definida
            echo "Error: La variable GITHUB_REPO no existe en el .env, tienes que indicar el enlace del repositorio sin el https://."
            exit 1
        elif [ -z "$LOCAL_REMOTE_REPO_ROUTE" ]; then    # Asegura que la ruta en formato Linux donde se clonará el repositorio exista
            echo "Error: La variable LOCAL_REMOTE_REPO_ROUTE no existe en el .env, tienes que indicar la ruta en formato Linux donde quieres que se clone el repositorio."
            exit 1
        elif [ -z "$LOCAL_REMOTE_REPO_ROUTE_TESTS" ]; then    # Asegura que la ruta en formato Linux del repositorio clonado donde se situan las suites exista
            echo "Error: La variable LOCAL_REMOTE_REPO_ROUTE_TESTS no existe en el .env, tienes que indicar la ruta en formato Linux donde están las suites del repositorio."
            exit 1
        elif [ -z "$LOCAL_REMOTE_REPO_ROUTE_REPORTS" ]; then    # Asegura que la ruta en formato Linux del repositorio clonado donde se situan los reportes exista
            echo "Error: La variable LOCAL_REMOTE_REPO_ROUTE_REPORTS no existe en el .env, tienes que indicar la ruta en formato Linux donde están los reportes del repositorio."
            exit 1
        else  # Una vez todo correcto procede a ejecutar el contenedor
            echo "Clonando repositorio remoto..."
            git clone https://${PAT}@${GITHUB_REPO} $LOCAL_REMOTE_REPO_ROUTE
            echo "Repositorio clonado!"
 
            # Se ejecuta el contenedor y se montan los volumenes, los tests suites que se quieren ejecutar se pueden modificar en el parametro -Dtest
            echo "Ejecutando el contenedor..."
            podman run --name "$CONTAINER_NAME" \
                --mount "type=bind,source=${LOCAL_REMOTE_REPO_ROUTE_TESTS},target=/app/src/test/java/web/TestSuites" \
                --mount "type=bind,source=${LOCAL_REMOTE_REPO_ROUTE_REPORTS},target=/app/resume-report" \
                "$IMAGE_NAME" mvn clean test -Dtest=$TEST_SUITE_NAME
            echo "Ejecución finalizada!"
           
            # En el caso que se quiera hacer commit de los resultados utilizará el token añadiendo como mensaje la fecha actual
            if [ "$COMMIT" == "YES" ]; then
                echo "Subiendo los reportes..."
                cd $LOCAL_REMOTE_ROUTE
                git add .
                git commit -m "Automated commit - $(date)"
                git push https://${USERNAME}:${PAT}@${GITHUB_REPO}
                cd ..
                echo "Reportes subidos!"
            fi
 
            # Borramos el contenedor y el repositorio para que no se haga commits innecesarios y el script se pueda reutilizar
            rm -rf "$LOCAL_REMOTE_ROUTE"
            podman rm $CONTAINER_NAME
 
        fi
    else # Si no está bien declarada la variable mostrará un error
        echo "Error: TEST_SUITE_ORIGIN debe ser LOCAL o REMOTE."
        exit 1
    fi
 
    echo "Proceso finalizado exitosamente!"
fi
 