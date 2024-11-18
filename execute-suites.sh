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
git clone https://${PAT}@${GITHUB_REPO} C:/ResourcesRepo

# Verify that the repository was cloned successfully
if [ ! -d "repo" ]; then
    echo "Failed to clone the repository."
    exit 1
fi

# Build the Docker image
podman build -t "$IMAGE_NAME" -f execution.dockerfile

# Run the container, mounting the directories
podman run -d --name "$CONTAINER_NAME" \
    -v "$REPO_PATH\Tests:/app/src/test/java/web/TestSuites" \
    -v "$REPO_PATH\Reports:/app/resume-report" \
    "$IMAGE_NAME" mvn clean test -Dtest=TESTWEB

# Wait for the container to finish
podman wait "$CONTAINER_NAME"

# Copy the resume-report directory from the container to the host
podman cp "$CONTAINER_NAME":/app/resume-report .

echo "Process completed successfully!"
