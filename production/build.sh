#!/bin/bash
set -x
set -e

# Build docker images

# Create a driver and use to build multiple platforms
docker buildx create --use

# Docker login
docker login -u techbuddy24 -p buddies@20


# Build App server Docker
#IMAGE_TAG="techbuddy24/goldendrop:app-server-v1"
#docker buildx build --platform=linux/amd64,linux/arm64 ~/waste/GoldenDrop/ --push --tag $IMAGE_TAG


## Build App server UI Docker
IMAGE_TAG="techbuddy24/goldendrop:app-server-ui-v1"
docker buildx build --platform=linux/amd64,linux/arm64 ~/waste/syndicate-ui/ --push --tag $IMAGE_TAG


