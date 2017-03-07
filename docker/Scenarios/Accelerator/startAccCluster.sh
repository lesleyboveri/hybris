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

echo "Waiting 15 seconds for mysql to start up.."
sleep 10

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
echo "Starting first platform node.."
echo "-------------------------------------------------"
mkdir -p /opt/hybris/acc_medias

sudo docker run \
  -d \
  -p 9001:9001 -p 9002:9002 \
  -e y_tomcat_http_port=9001 -e y_tomcat_ssl_port=9002 \
  -e CATALINA_JAVA_OPTS="-Xmx1500m -XX:MaxPermSize=350M -ea -Dcom.sun.management.jmxremote \
     -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false \
     -Dorg.tanukisoftware.wrapper.WrapperManager.mbean=true -Dfile.encoding=UTF-8" \
  -v /opt/hybris/acc_medias:/etc/hybris/data/media \
  --env-file ./acc_env \
  --link y.s.solr:solr \
  --link y.s.mysql:mysql \
  --name y.s.first \
  y.i.accelerator

echo "-------------------------------------------------"
echo "Starting second node.."
echo "-------------------------------------------------"
sudo docker run \
  -d \
  -p 9011:9001 -p 9012:9002 \
  -e y_tomcat_http_port=9011 -e y_tomcat_ssl_port=9012 \
  -e CATALINA_JAVA_OPTS="-Xmx1500m -XX:MaxPermSize=350M -ea -Dcom.sun.management.jmxremote \
     -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false \
     -Dorg.tanukisoftware.wrapper.WrapperManager.mbean=true -Dfile.encoding=UTF-8" \
  --env-file ./acc_env \
  --volumes-from y.s.first \
  --link y.s.solr:solr \
  --link y.s.mysql:mysql \
  y.i.accelerator

docker ps

docker logs -f y.s.first
