#!/bin/bash
set -e

NAME_PARAM=$1

IMAGE_NAME="${NAME_PARAM:-sprcom.hybris.platform}"

echo "Building ${IMAGE_NAME}"

docker build -t "${IMAGE_NAME}" .
