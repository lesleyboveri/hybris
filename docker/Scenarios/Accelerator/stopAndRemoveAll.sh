#!/bin/sh
set -e

ROOTUID="0"

if [ "$(id -u)" -ne "$ROOTUID" ] ; then
    echo "This script must be executed with root privileges."
    exit 1
fi

echo "-------------------------------------------------"
echo "Stopping all running containers.."
echo "-------------------------------------------------"

docker stop $(docker ps -q)


echo "-------------------------------------------------"
echo "Removing all containers.."
echo "-------------------------------------------------"

docker rm $(docker ps -q -a)
