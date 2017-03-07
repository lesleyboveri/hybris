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

echo "Waiting 10 seconds for mysql to start up.."
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
  -e y_tomcat_http_port=8080 -e y_tomcat_ssl_port=8443 \
  -e CATALINA_JAVA_OPTS="-Xms1500m -Xmx1500m -XX:MaxPermSize=350M -ea -Dcom.sun.management.jmxremote \
     -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false \
     -Dorg.tanukisoftware.wrapper.WrapperManager.mbean=true -Dfile.encoding=UTF-8" \
  -e TOMCAT_SERVER_XML="server_proxy_nossl.xml" \
  -e TC_HTTP_PROXY_PORT=8080 \
  -e TC_HTTP_REDIRECT_PORT=8443 \
  -e TC_HTTPS_PROXY_PORT=8443 \
  -e y_storefront_staticResourceFilter_response_header_Cache-Control=public,max-age=600 \
  -v /opt/hybris/acc_medias:/etc/hybris/data/media \
  --env-file ./acc_env \
  --link y.s.solr:solr \
  --link y.s.mysql:mysql \
  --name y.s.acc_1 \
  y.i.accelerator

echo "-------------------------------------------------"
echo "Starting second node.."
echo "-------------------------------------------------"
sudo docker run \
  -d \
  -e y_tomcat_http_port=8080 -e y_tomcat_ssl_port=8443 \
  -e CATALINA_JAVA_OPTS="-Xms1500m -Xmx1500m -XX:MaxPermSize=350M -ea -Dcom.sun.management.jmxremote \
     -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false \
     -Dorg.tanukisoftware.wrapper.WrapperManager.mbean=true -Dfile.encoding=UTF-8" \
  -e TOMCAT_SERVER_XML="server_proxy_nossl.xml" \
  -e TC_HTTP_PROXY_PORT=8080 \
  -e TC_HTTP_REDIRECT_PORT=8443 \
  -e TC_HTTPS_PROXY_PORT=8443 \
  -e y_storefront_staticResourceFilter_response_header_Cache-Control=public,max-age=600 \
  --env-file ./acc_env \
  --volumes-from y.s.acc_1 \
  --link y.s.solr:solr \
  --link y.s.mysql:mysql \
  --name y.s.acc_2 \
  y.i.accelerator

echo "Waiting 15 seconds for platforms to start up.."
sleep 15

echo "-------------------------------------------------"
echo "Starting Apache as Load Balancer.."
echo "-------------------------------------------------"
sudo docker run \
  -d \
  -p 8080:8080 -p 8443:8443 \
  --link y.s.acc_1:acc_1 \
  --link y.s.acc_2:acc_2 \
  y.i.apache

docker ps
