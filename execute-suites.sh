#!/bin/bash
set -x  # Enable debug mode

IMAGE_NAME="execution-image"
CONTAINER_NAME="execution-container"
GITHUB_REPO="github.com/PabloCastillo-Inetum/DevOps_Docker.git"

# Remove and initialize Podman machine
podman machine rm -f
podman machine init
podman machine start

# Load the PAT from the .env file
if [ -f ".env" ]; then
    export $(grep -v '^#' .env | xargs)
else
    echo "The .env file is missing. Please create one with your environment variables."
    exit 1
fi

# Verify the PAT is set
if [ -z "$PAT" ]; then
    echo "The Personal Access Token (PAT) is not defined in the .env file."
    exit 1
fi

# Clone the repository locally
echo "Cloning the repository..."
git clone https://${PAT}@${GITHUB_REPO} repo

# Verify that the repository was cloned successfully
if [ ! -d "repo" ]; then
    echo "Failed to clone the repository."
    exit 1
fi

# Build the Docker image
podman build -t "$IMAGE_NAME" -f execution.dockerfile

# Convert Windows paths to Linux paths for Podman

# Run the container, mounting the directories
podman run --name "$CONTAINER_NAME" \
  -v "./repo/tests:/app/src/test/java/web/TestSuites" \
  -v "./repo/resume-report:/app/resume-report" \
  "$IMAGE_NAME" clean test -Dtest=TESTWEB

# Wait for the container to finish
podman wait "$CONTAINER_NAME"

# Verify if the resume-report directory exists
podman exec "$CONTAINER_NAME" ls -l /app/resume-report

# Copy the resume-report directory from the container to the host
podman cp "$CONTAINER_NAME":/app/resume-report .

echo "Process completed successfully!"

# Optional: Clean up the cloned repository
# rm -rf repo
