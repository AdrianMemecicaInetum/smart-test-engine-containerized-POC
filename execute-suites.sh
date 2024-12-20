#!/bin/bash
set -e  # Se aborta si hay algun error
 
# Carga el .evn si existe
if [ -f .env ]; then
    # Exporta las variables del .env
    export $(grep -v '^#' .env | xargs)
fi
 
# Variables configurables
#Nombre de la imagen subida a dockerhub, que contiene el STE
NOMBRE_IMAGEN="adrianinetum2/smarttestengine:v1"
#Nombre del contenedor que se ejecutara
NOMBRE_CONTENEDOR="execution-container"
#Nombre de usuario de Github con acceso al repositorio
USUARIO_GITHUB="PabloCastillo-Inetum"
#Metodo con el que se accederá a las suites y se tratarán los reportes (LOCAL/REMOTE)
ORIGEN_DATOS="REMOTE"
#Nombre de la suite que se quiere ejecutar
SUITE_A_EJECUTAR="TESTWEB"
#Parámetro que indica el contenido del repositorio (1=Solo Suites / 2=Solo Reportes / 3=Todo)
DATOS_REPOSITORIO="1"
#Accion que indica si se quiere subir el commit del resultado en el mismo repositorio o en otro diferente (1=Mismo Repositorio / 2=Diferente Repositorio)
COMMIT="1"
 
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
    if [ "$ORIGEN_DATOS" == "LOCAL" ]; then
        if [ -z "$RUTA_SUITES_LOCAL" ]; then # Asegura que la ruta en formato Linux donde se encuentran los tests exista  
            echo "Error: La variable RUTA_SUITES_LOCAL no existe en el .env, tienes que indicar la ruta en formato Windows donde se encuentran la suite a ejecutar."
            exit 1
        elif [ -z "$RUTA_REPORTES_LOCAL" ]; then  # Asegura que la ruta en formato Linux donde se guardarán los reportes exista
            echo "Error: La variable REPOSITORIO_GITHUB_ORIGEN no existe en el .env, tienes que indicar la ruta en formato Windows donde quieres que se guarden los reportes."
            exit 1
        else  # Una vez todo correcto procede a ejecutar el contenedor, una vez finaliza borra el contenedor para futuras ejecuciones
            echo "Ejecutando el contenedor con suites en local y reportes en local..."
            podman run --name "$NOMBRE_CONTENEDOR" \
                --mount "type=bind,source=${RUTA_SUITES_LOCAL},target=/app/src/test/java/web/TestSuites" \
                --mount "type=bind,source=${RUTA_REPORTES_LOCAL},target=/app/resume-report" \
                "$NOMBRE_IMAGEN" mvn clean test -Dtest=$SUITE_A_EJECUTAR
            podman rm $NOMBRE_CONTENEDOR
            echo "Ejecución finalizada!"
        fi
 
    # En el caso de que la ejecución sea con datos en remoto:
    elif [ "$ORIGEN_DATOS" == "REMOTE" ]; then
        if [ -z "$TOKEN_ORIGEN" ]; then     # Asegura que el token de acceso a github esté definido
            echo "Error: La variable TOKEN_ORIGEN no existe en el .env, tienes que indicar el token de acceso para trabajar con el repositorio."
            exit 1
        elif [ -z "$REPOSITORIO_GITHUB_ORIGEN" ]; then    # Asegura que la ruta del repositorio de github esté definida
            echo "Error: La variable REPOSITORIO_GITHUB_ORIGEN no existe en el .env, tienes que indicar el enlace del repositorio sin el https://."
            exit 1
        elif [ -z "$RUTA_REPOSITORIO_ORIGEN" ]; then    # Asegura que la ruta en formato Linux donde se clonará el repositorio exista
            echo "Error: La variable RUTA_REPOSITORIO_ORIGEN no existe en el .env, tienes que indicar la ruta en formato Linux donde quieres que se clone el repositorio."
            exit 1
        elif [ -z "$RUTA_SUITES_REPOSITORIO_CLONADO" ]; then    # Asegura que la ruta en formato Linux del repositorio clonado donde se situan las suites exista
            echo "Error: La variable RUTA_SUITES_REPOSITORIO_CLONADO no existe en el .env, tienes que indicar la ruta en formato Linux donde están las suites del repositorio."
            exit 1
        elif [ -z "$RUTA_REPORTES_REPOSITORIO_CLONADO" ]; then    # Asegura que la ruta en formato Linux del repositorio clonado donde se situan los reportes exista
            echo "Error: La variable RUTA_REPORTES_REPOSITORIO_CLONADO no existe en el .env, tienes que indicar la ruta en formato Linux donde están los reportes del repositorio."
            exit 1
        else  # Una vez todo correcto procede a ejecutar el contenedor
            echo "Clonando repositorio remoto..."
            git clone https://${TOKEN_ORIGEN}@${REPOSITORIO_GITHUB_ORIGEN} $RUTA_REPOSITORIO_ORIGEN
            echo "Repositorio clonado!"
 
            # Se ejecuta el contenedor y se montan los volumenes, en función de los datos que haya en el repositorio clonado toma diferentes rutas para estos.
            if [ "$DATOS_REPOSITORIO" == "1" ]; then  # En el caso que en el repositorio únicamente estén las suites y se utilizen reportes en local:
                echo "Ejecutando el contenedor con suites en remoto y reportes en local..."
                podman run --name "$NOMBRE_CONTENEDOR" \
                    --mount "type=bind,source=${RUTA_SUITES_REPOSITORIO_CLONADO},target=/app/src/test/java/web/TestSuites" \
                    --mount "type=bind,source=${RUTA_REPORTES_LOCAL},target=/app/resume-report" \
                    "$NOMBRE_IMAGEN" mvn clean test -Dtest=$SUITE_A_EJECUTAR
                echo "Ejecución finalizada!"
 
            elif [ "$DATOS_REPOSITORIO" == "2" ]; then  # En el caso que en el repositorio únicamente estén los reportes y se utilizen suites en local:
                echo "Ejecutando el contenedor con suites en local y reportes en remoto..."
                podman run --name "$NOMBRE_CONTENEDOR" \
                    --mount "type=bind,source=${RUTA_SUITES_LOCAL},target=/app/src/test/java/web/TestSuites" \
                    --mount "type=bind,source=${RUTA_REPORTES_REPOSITORIO_CLONADO},target=/app/resume-report" \
                    "$NOMBRE_IMAGEN" mvn clean test -Dtest=$SUITE_A_EJECUTAR
                echo "Ejecución finalizada!"
 
            elif [ "$DATOS_REPOSITORIO" == "3" ]; then  # En el caso que en el repositorio estén ambos datos:
                echo "Ejecutando el contenedor con suites en remoto y reportes en remoto..."
                podman run --name "$NOMBRE_CONTENEDOR" \
                    --mount "type=bind,source=${RUTA_SUITES_REPOSITORIO_CLONADO},target=/app/src/test/java/web/TestSuites" \
                    --mount "type=bind,source=${RUTA_REPORTES_REPOSITORIO_CLONADO},target=/app/resume-report" \
                    "$NOMBRE_IMAGEN" mvn clean test -Dtest=$SUITE_A_EJECUTAR
                echo "Ejecución finalizada!"
 
            else  # Caso en el que el valor de la variable DATOS_REPOSITORIO no sea valido
                echo "Error: La variable DATOS_REPOSITORIO debe contener como valor (1 / 2 / 3)"
                exit 1
            fi
 
            # Este apartado está hecho de esta forma para no confundir al usuario/cliente, ya que en vez de crear este condicional simplemente se podría utilizar el run
            # de la condición 3 y que el valor de "RUTA_SUITES_REPOSITORIO_CLONADO" y "RUTA_REPORTES_REPOSITORIO_CLONADO" sean las rutas en local. Lo que para que quede
            # más "entendible" utilizamos las variables en local según la opción.
           
            # En el caso que se quiera hacer commit y subir el reporte en el mismo repositorio donde se han obtenido los datos:
            if [ "$COMMIT" == "1" ] && { [ "$DATOS_REPOSITORIO" == "2" ] || [ "$DATOS_REPOSITORIO" == "3" ]; }; then
                echo "Subiendo los reportes al mismo repositorio..."
                cd $RUTA_REPOSITORIO_ORIGEN_WINDOWS
                git add .
                git commit -m "Automated commit - $(date)"
                git push https://${USUARIO_GITHUB}:${TOKEN_ORIGEN}@${REPOSITORIO_GITHUB_ORIGEN}
                cd ..
                echo "Reportes subidos!"
 
                # Borramos el contenedor y el repositorio para que no se haga commits innecesarios y el script se pueda reutilizar
                rm -rf "$RUTA_REPOSITORIO_ORIGEN_WINDOWS"
                podman rm $NOMBRE_CONTENEDOR
 
            # En el caso que se quiera hacer commit y subir el reporte en otro repositorio diferente:
            elif [ "$COMMIT" == "2" ]; then
                if [ -z "$TOKEN_DESTINO" ]; then     # Asegura que el token de acceso al repositorio de github de destino esté definido
                    echo "Error: La variable TOKEN_DESTINO no existe en el .env, tienes que indicar el token de acceso para trabajar con el repositorio."
                    exit 1
                elif [ -z "$REPOSITORIO_GITHUB_DESTINO" ]; then    # Asegura que la ruta del repositorio de github de destino esté definida
                    echo "Error: La variable REPOSITORIO_GITHUB_DESTINO no existe en el .env, tienes que indicar el enlace del repositorio sin el https://."
                    exit 1
                elif [ -z "$RUTA_REPOSITORIO_DESTINO" ]; then    # Asegura que la ruta en formato Linux donde se clonará el repositorio de destino exista
                    echo "Error: La variable RUTA_REPOSITORIO_DESTINO no existe en el .env, tienes que indicar la ruta en formato Linux donde quieres que se clone el repositorio."
                    exit 1
                else   # En el caso de que todo exista procede a clonar el otro repositorio y luego hace el push
                    echo "Clonando repositorio de destino..."
                    git clone https://${TOKEN_DESTINO}@${REPOSITORIO_GITHUB_DESTINO} $RUTA_REPOSITORIO_DESTINO
                    echo "Repositorio clonado!"
                    echo "Subiendo los reportes al repositorio de destino..."
                    cd $RUTA_REPOSITORIO_DESTINO_WINDOWS
                    git add .
                    git commit -m "Automated commit - $(date)"
                    git push https://${USUARIO_GITHUB}:${TOKEN_DESTINO}@${REPOSITORIO_GITHUB_DESTINO}
                    cd ..
                    echo "Reportes subidos!"
 
                    # Borramos el contenedor y los repositorios para que no se haga commits innecesarios y el script se pueda reutilizar
                    rm -rf "$RUTA_REPOSITORIO_ORIGEN_WINDOWS"
                    rm -rf "$RUTA_REPOSITORIO_DESTINO_WINDOWS"
                    podman rm $NOMBRE_CONTENEDOR
 
                fi
 
            elif [ "$DATOS_REPOSITORIO" == "1" ]; then
                echo "El reporte se ha generado en la carpeta local pero no se ha subido a ningún repositorio."
 
            else # Caso en el que el valor de la variable COMMIT no sea valido
                echo "Error: La variable COMMIT debe contener como valor (1 / 2)"
                rm -rf "$RUTA_REPOSITORIO_ORIGEN_WINDOWS"
                podman rm $NOMBRE_CONTENEDOR
                exit 1
 
            fi
        fi
    else # Si no está bien declarada la variable mostrará un error
        echo "Error: ORIGEN_DATOS debe ser LOCAL o REMOTE."
        exit 1
    fi
 
    echo "Proceso finalizado exitosamente!"
fi