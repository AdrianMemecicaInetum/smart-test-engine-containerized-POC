# entrypoint.sh
#!/bin/bash CREAR LA BUILD Y LUEGO EJECUTAR LO DE DESPUES

IMAGE_NAME="execution-image"
CONTAINER_NAME="execution-container"
GITHUB_REPO2="github.com/PabloCastillo-Inetum/DevOps_Docker.git"
PAT="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"

podman build --build-arg GIT_PAT="$PAT" --build-arg GIT_REPO_URL="$GITHUB_REPO2" -t "$IMAGE_NAME" -f execution.dockerfile .

podman run -d --name "$CONTAINER_NAME" "$IMAGE_NAME" mvn clean test -Dtest=TESTWEB

sleep 540

podman exec "$CONTAINER_NAME" rm -rf /home/Reports/*
podman exec "$CONTAINER_NAME" whoami
echo "Carpeta framework:"
podman exec "$CONTAINER_NAME" ls -l resume-report/
echo "Carpeta git:"
podman exec "$CONTAINER_NAME" ls -l /home/Reports/
echo "Carpeta actual:"
podman exec "$CONTAINER_NAME" ls -l 
podman exec "$CONTAINER_NAME" cp -r /app/resume-report/* /home/Reports/
podman exec "$CONTAINER_NAME" bash -c "cd /home && git add . && git commit -m 'Updated Reports' && git push"

echo "Process completed successfully."