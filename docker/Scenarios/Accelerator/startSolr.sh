#!/bin/sh
set -e

ROOTUID="0"

if [ "$(id -u)" -ne "$ROOTUID" ] ; then
    echo "This script must be executed with root privileges."
    exit 1
fi

echo "-------------------------------------------------"
echo "Start Solr.."
echo "-------------------------------------------------"
mkdir -p /opt/hybris/solr/data

sudo docker run \
  -d \
  -v /opt/hybris/solr/data:/opt/solr/server/solr/cores \
  --name y.s.solr \
  y.i.solr

echo "-------------------------------------------------"
echo "Showing Solr logs - exit via CTRL-C"
echo "-------------------------------------------------"

docker logs -f y.s.solr
