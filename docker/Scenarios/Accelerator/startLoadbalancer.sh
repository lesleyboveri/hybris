#!/bin/sh
set -e

ROOTUID="0"

if [ "$(id -u)" -ne "$ROOTUID" ] ; then
    echo "This script must be executed with root privileges."
    exit 1
fi

echo "-------------------------------------------------"
echo "Starting Apache as Load Balancer.."
echo "-------------------------------------------------"
sudo docker run \
  -d \
  -p 8080:8080 -p 8443:8443 \
  --link y.s.acc_1:acc_1 \
  --link y.s.acc_2:acc_2 \
  --name y.s.apache \
  y.i.apache

echo "-------------------------------------------------"
echo "Showing Apache logs - exit via CTRL-C"
echo "-------------------------------------------------"

docker logs -f y.s.apache
