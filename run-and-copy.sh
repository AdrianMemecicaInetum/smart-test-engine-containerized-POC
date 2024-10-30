#!/bin/bash
podman machine stop
podman machine rm -f
podman machine init
podman machine start
# Load environment variables from .env file
if [ -f .env ]; then
  set -o allexport
  source .env
  set +o allexport
else
  echo ".env file not found!"
  exit 1
fi

# Check if required variables are set
if [ -z "$PAT" ] || [ -z "$GITHUB_REPO" ]; then
  echo "PAT and GITHUB_REPO must be set in the .env file."
  exit 1
fi

IMAGE_NAME="smart-test-engine-container"
CONTAINER_NAME="smart-test-container"
HOST_DESTINATION_DIR="."

# Build the Docker image
 podman build --build-arg GIT_PAT="$PAT" --build-arg GIT_REPO_URL="$GITHUB_REPO" -t "$IMAGE_NAME" -f smart-test-engine.dockerfile . 

# Run tests in the container
podman run  -d --name "$CONTAINER_NAME" "$IMAGE_NAME" 

# Copy the resume-report folder to the host
echo "Copying resume-report from container to host..."
podman cp "$CONTAINER_NAME":/app/resume-report "$HOST_DESTINATION_DIR"

# Clean up the container
# echo "Cleaning up the container..."
# podman rm "$CONTAINER_NAME"

echo "Process completed successfully."

