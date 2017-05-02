#!/bin/sh

docker run \
  -d \
  -e y_tomcat_http_port=8080 -e y_tomcat_ssl_port=8443 \
  -e CATALINA_JAVA_OPTS="-Xms1500m -Xmx1500m -XX:MaxPermSize=350M -ea -Dcom.sun.management.jmxremote \
  -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false \
  -Dorg.tanukisoftware.wrapper.WrapperManager.mbean=true -Dfile.encoding=UTF-8" \
  -e TC_HTTP_PROXY_PORT=8080 \
  -e TC_HTTP_REDIRECT_PORT=8443 \
  -e TC_HTTPS_PROXY_PORT=8443 \
  -e y_storefront_staticResourceFilter_response_header_Cache-Control=public,max-age=600 \
  -p 8080:8080 -p 8443:8443 \
  -p 9001:9001 -p 9002:9002 \
  --env-file ./acc_env \
  --name app-01 \
sprcom.hybris.platform
