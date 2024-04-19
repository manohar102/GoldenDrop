#!/bin/bash
set -x
set -e

# Build docker images

# Create a driver and use to build multiple platforms
docker buildx create --use


# Build App server Docker
IMAGE_TAG="techbuddy/goldendrop:app-server"
docker buildx build --platform=linux/arm64 ../GoldenDrop/ --load --tag $IMAGE_TAG


# Build App server UI Docker
IMAGE_TAG="techbuddy/goldendrop:app-server-ui"
docker buildx build --platform=linux/arm64 ../../syndicate-ui/ --load --tag $IMAGE_TAG


