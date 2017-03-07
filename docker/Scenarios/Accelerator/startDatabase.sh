#!/bin/sh
set -e

ROOTUID="0"

if [ "$(id -u)" -ne "$ROOTUID" ] ; then
    echo "This script must be executed with root privileges."
    exit 1
fi

echo "-------------------------------------------------"
echo "Start MySQL.."
echo "-------------------------------------------------"
mkdir -p /opt/hybris/mysql/lib

sudo docker run \
  -d \
  -v /opt/hybris/mysql/lib:/var/lib/mysql \
  --name y.s.mysql \
  y.i.mysql

echo "-------------------------------------------------"
echo "Showing logs from y.i.mysql - exit via CTRL-C "
echo "-------------------------------------------------"

docker logs -f y.s.mysql
