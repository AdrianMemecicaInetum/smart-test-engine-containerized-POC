# Contenedor de Smart Test Engine

Este proyecto construye un contenedor para ejecutar pruebas en el Smart Test Engine. Requiere un Token de Acceso Personal de GitHub (PAT) para clonar el repositorio privado. Sigue los pasos a continuación para configurar el proyecto.

## Tabla de Contenidos

- [Requisitos previos](#requisitos-previos)
- [Configuración del Token de Acceso Personal de GitHub (PAT)](#configuración-del-token-de-acceso-personal-de-github-pat)
- [Creación del archivo `.env`](#creación-del-archivo-env)
- [Construcción y Ejecución del Contenedor](#construcción-y-ejecución-del-contenedor)

## Requisitos previos

- [Podman](https://podman.io/)
- Token de Acceso Personal de GitHub (PAT)

## Configuración del Token de Acceso Personal de GitHub (PAT)

Para clonar un repositorio privado, necesitas crear un Token de Acceso Personal (PAT) de GitHub con los permisos específicos.

1. Sigue este [tutorial para generar un PAT de GitHub](https://www.geeksforgeeks.org/how-to-generate-personal-access-token-in-github/).
2. Al crear el PAT, asegúrate de seleccionar los siguientes permisos:
   
   ![Permisos PAT](resources/ss-generate-PAT-permissions.png) <!-- Inserta tu imagen aquí -->

3. Copia el PAT generado. Necesitarás añadirlo al archivo `.env` como se describe a continuación.

## Creación del archivo `.env`

1. En el directorio raíz del proyecto, crea un archivo `.env` con el siguiente contenido:

    ```bash
    PAT=tu_pat_de_github_aquí
    GITHUB_REPO=github.com/TuUsuario/TuRepo.git
    ```

   **Nota:** No incluyas `https://` en la variable `GITHUB_REPO`.

   Ejemplo:

    ```bash
    PAT=github_pat_
    GITHUB_REPO=github.com/Usuario/smart-test-engine-container.git
    ```

## Construcción y Ejecución del Contenedor

Una vez que hayas configurado el archivo `.env`:

1. Inicializa la maquina de podman

    ```bash
        podman machine init
    ```

2. Arranca la maquina:

    ```bash
    podman machine start
    ```

3. Ejecuta el script run-and-copy.sh, que construye y ejecuta el contenedor:

    ```bash
    ./run-and-copy.sh
    ```


---

