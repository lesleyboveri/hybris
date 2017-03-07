#!/bin/sh
set -e

ROOTUID="0"

if [ "$(id -u)" -ne "$ROOTUID" ] ; then
    echo "This script must be executed with root privileges."
    exit 1
fi

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
  --env-file ./acc_env \
  --volumes-from y.s.acc_1 \
  --link y.s.solr:solr \
  --link y.s.mysql:mysql \
  --name y.s.acc_2 \
  y.i.accelerator

docker logs -f y.s.acc_2
