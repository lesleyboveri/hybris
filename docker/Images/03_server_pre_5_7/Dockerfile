FROM y.i.tomcat
MAINTAINER axel.grossmann@hybris.de

# admin files
ENV HYBRIS_ADMIN /hybrisAdmin
RUN mkdir -p ${HYBRIS_ADMIN}/utils
ADD util/*.jar ${HYBRIS_ADMIN}/util/

# admin properties for platform
ENV HYBRIS_OPT_CONFIG_DIR /etc/hybris/localconfig
RUN mkdir -p ${HYBRIS_OPT_CONFIG_DIR}

RUN mkdir -p /etc/hybris/data
RUN mkdir -p /etc/hybris/temp
RUN mkdir -p /etc/hybris/log

# additional platform properties for overriding local.properties
ADD platform_config/*.properties ${HYBRIS_OPT_CONFIG_DIR}/
RUN echo "" >> ${HYBRIS_OPT_CONFIG_DIR}/env.properties &&\
    echo "HYBRIS_DATA_DIR=/etc/hybris/data" >> ${HYBRIS_OPT_CONFIG_DIR}/env.properties &&\
    echo "HYBRIS_LOG_DIR=/etc/hybris/log" >> ${HYBRIS_OPT_CONFIG_DIR}/env.properties &&\
    echo "HYBRIS_TEMP_DIR=/etc/hybris/temp" >> ${HYBRIS_OPT_CONFIG_DIR}/env.properties

# tomcat location for start script
ENV TOMCAT /opt/tomcat
# tomcat:  db drivers, other libs
ADD tomcat_lib/* ${TOMCAT}/lib/
# tomcat:  conf (several server_xxx.xml variants)
ADD tomcat_conf/* ${TOMCAT}/conf/
# default server_xxx.xml variant: server_standalone.xml
ENV TOMCAT_SERVER_XML server_standalone.xml

ENV TC_HTTP_PORT 9001
ENV TC_HTTP_PROXY_PORT 80
ENV TC_HTTP_REDIRECT_PORT 443

ENV TC_HTTPS_PORT 9002
ENV TC_HTTPS_PROXY_PORT 443

ENV TC_AJP_PORT=9009

ENV TC_JMX_PORT 9003
ENV TC_JMX_SERVER_PORT 9004

# start command
ADD startHybrisTomcat.sh /usr/local/bin/startHybrisTomcat

# ENV->properies prefix
ENV PROPERTY_ENV_PREFIX y_

# expected platform production package
ENV PLATFORM_PACKAGE /hybrisServer

# catalina opts fragments
ENV CATALINA_JAVA_OPTS -Xms2G -Xmx2G -XX:MaxPermSize=300M -ea -Dcom.sun.management.jmxremote \
-Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false \
-Dorg.tanukisoftware.wrapper.WrapperManager.mbean=true -Dfile.encoding=UTF-8

CMD ["/usr/local/bin/startHybrisTomcat"]
