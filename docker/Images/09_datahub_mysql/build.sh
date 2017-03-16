#!/bin/bash
set -e

NAME_PARAM=$1

IMAGE_NAME="${NAME_PARAM:-sprcom.hybris.datahub.mysql}"

echo "Building ${IMAGE_NAME}"

sudo docker build -t "${IMAGE_NAME}" .
