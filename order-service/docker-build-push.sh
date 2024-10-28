#!/bin/bash

IMAGE_NAME="order-service"
DOCKERHUB_USERNAME="mariesto"
TAG="latest"

FULL_IMAGE_NAME="$DOCKERHUB_USERNAME/$IMAGE_NAME:$TAG"

echo "Building Docker image..."
docker build -t $FULL_IMAGE_NAME .

echo "Pushing Docker image to Docker Hub..."
docker push $FULL_IMAGE_NAME


# Uncomment the line below if you want to automatically rollout restart your Kubernetes deployment
# kubectl rollout restart deployment $IMAGE_NAME

echo "Done! Image $FULL_IMAGE_NAME has been built and pushed."
