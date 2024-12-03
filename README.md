# Contenedor de Smart Test Engine

Este proyecto permite ejecutar pruebas de **Smart Test Engine** dentro de un contenedor utilizando **Podman**. Automatiza el proceso de clonación de un repositorio con los tests, la ejecución de las pruebas y la subida de los reportes generados al repositorio.

## Tabla de Contenidos

- [Requisitos Previos](#requisitos-previos)
- [Configuración del Token de Acceso Personal de GitHub (PAT)](#configuración-del-token-de-acceso-personal-de-github-pat)
- [Creación del Archivo `.env`](#creación-del-archivo-env)
- [Ejecución del Script](#ejecución-del-script)
- [Explicación del Funcionamiento](#explicación-del-funcionamiento)
- [Notas Adicionales](#notas-adicionales)

## Requisitos Previos

- [Podman](https://podman.io/) instalado en tu sistema.
- Un repositorio de GitHub que contenga:
  - Carpeta `Tests` con los tests que se desean ejecutar.
  - Carpeta `Reports` donde se guardarán los reportes generados.
- Un archivo `.env` con tu Token de Acceso Personal (PAT) y la URL del repositorio.

## Configuración del Token de Acceso Personal de GitHub (PAT)

Para clonar y hacer push a un repositorio privado, necesitas un Token de Acceso Personal (PAT) de GitHub con los permisos adecuados.

1. Sigue este [tutorial para generar un PAT de GitHub](https://www.geeksforgeeks.org/how-to-generate-personal-access-token-in-github/).
2. Crea un token fine-grained.
2. Al crear el PAT, selecciona los siguientes permisos:

   ![Permisos PAT](resources/ss-generate-PAT-permissions.png)

3. Copia el PAT generado; lo necesitarás para el archivo `.env`.

## Creación del Archivo `.env`

1. En el directorio raíz del proyecto, crea un archivo llamado `.env`.
2. Añade las siguientes variables:

    ```bash
    PAT=tu_token_de_acceso_personal
    GITHUB_REPO_URL=github.com/tu_usuario/tu_repositorio.git
    USERNAME=tu_usuario
    ```

   **Nota:** No incluyas `https://` en la URL del repositorio.

## Ejecución del Script

1. Asegúrate de que Podman está instalado y funcionando correctamente.
2. Da permisos de ejecución al script si es necesario:

    ```bash
    chmod +x execute-suites.sh
    ```

3. Ejecuta el script:

    ```bash
    ./execute-suites.sh
    ```

   **Nota:** El script se encargará de inicializar Podman, clonar el repositorio, ejecutar los tests y subir los reportes generados.

## Explicación del Funcionamiento

El script `execute-suites.sh` realiza las siguientes acciones:

1. **Carga de Variables de Entorno:**

   - Si existe un archivo `.env`, carga las variables definidas en él.

2. **Verificación del PAT:**

   - Comprueba que la variable `PAT` está definida. Si no, el script se detiene con un mensaje de error.

3. **Clonación del Repositorio:**

   - Utiliza `git clone` junto con el `PAT` para clonar el repositorio especificado en `GITHUB_REPO_URL`.

4. **Inicialización de Podman:**

   - Elimina cualquier máquina de Podman existente con `podman machine rm -f`.
   - Inicializa y arranca una nueva máquina de Podman con `podman machine init` y `podman machine start`.

5. **Ejecución del Contenedor:**

   - Ejecuta un contenedor con la imagen especificada en `IMAGE_NAME`.
   - Monta las carpetas `Tests` y `Reports` del repositorio clonado en los puntos adecuados dentro del contenedor.
   - Ejecuta los tests utilizando Maven: `mvn clean test -Dtest=TESTWEB`.

6. **Espera a que el Contenedor Termine:**

   - Utiliza `podman wait` para esperar hasta que el contenedor finalice su ejecución.

7. **Subida de Reportes al Repositorio:**

   - Navega al directorio del repositorio clonado.
   - Añade todos los cambios con `git add .`.
   - Realiza un commit con un mensaje automático que incluye la fecha.
   - Pushea los cambios al repositorio remoto con `git push https://${USERNAME}:${PAT}@${GITHUB_REPO_URL}`.

8. **Limpieza:**

   - Regresa al directorio anterior y elimina el directorio del repositorio clonado con `rm -rf repo` para que no se haga commits innecesarios y el script se pueda reutilizar

## Notas Adicionales

- **Personalización del Script:**

  - Puedes modificar las variables en el script `execute-suites.sh` según tus necesidades:

    ```bash
    IMAGE_NAME="adrianinetum2/smarttestengine:v1"
    CONTAINER_NAME="execution-container"
    GITHUB_REPO_URL="github.com/tu_usuario/tu_repositorio.git"
    CURRENT_DIR="/ruta/a/tu/directorio"
    ```

- **Estructura del Repositorio:**

  - Asegúrate de que tu repositorio tiene las carpetas `Tests` y `Reports` en la raíz. Esto es  para que el script pueda montar correctamente los volúmenes en el contenedor.

- **Solución de Problemas:**

  - Si encuentras problemas al ejecutar el script, verifica que:
    - El `PAT` tiene los permisos adecuados.
    - La URL del repositorio en `GITHUB_REPO_URL` es correcta.
    - Podman está instalado y funcionando correctamente.

- **Ejemplo de Archivo `.env`:**

    ```bash
    PAT=github_pat_1234567890abcdef1234567890abcdef678
    GITHUB_REPO_URL=github.com/usuario/smart-test-engine-container.git
    ```

