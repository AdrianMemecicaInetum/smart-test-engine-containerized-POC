#!/bin/bash
set -e  # Exit on errors

# Load environment variables from .env file if it exists
if [ -f .env ]; then
    # Export all variables from .env file
    export $(grep -v '^#' .env | xargs)
fi

# Configurable Variables
IMAGE_NAME="adrianinetum2/smarttestengine:v1"
CONTAINER_NAME="execution-container"
GITHUB_REPO_URL="github.com/PabloCastillo-Inetum/DevOps_Docker.git"
CURRENT_DIR="/c/Users/adrian.memecica/Desktop/smart-test-engine-containerized-POC/v2.0/smart-test-engine-containerized-POC"

# Ensure PAT is passed as an environment variable
if [ -z "$PAT" ]; then
    echo "Error: PAT is not set. Exiting."
    exit 1
fi

# Clone the repository on the host
echo "Cloning repository..."
git clone https://${PAT}@${GITHUB_REPO_URL} repo

# Run the container and mount the necessary folders
echo "Running container..."
podman run --name "$CONTAINER_NAME" \
    --mount type=bind,source="$CURRENT_DIR/repo/Tests",target=/app/src/test/java/web/TestSuites \
    --mount type=bind,source="$CURRENT_DIR/repo/Reports",target=/app/resume-report \
    "$IMAGE_NAME" mvn clean test -Dtest=TESTWEB


# Wait for the container to finish
echo "Waiting for the container to finish..."
podman wait "$CONTAINER_NAME"


# Cleanup


echo "Process completed successfully."
