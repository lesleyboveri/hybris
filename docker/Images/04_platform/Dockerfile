FROM docker-registry.dc.springernature.pe/sprcom/sprcom.hybris.server:latest
MAINTAINER axel.grossmann@hybris.de

ADD hybrisServer/hybrisServer-*.zip /tmp/

# unzip platform into PLATFORM_PACKAGE ( defined in y.i.hybrisserver )
RUN for z in /tmp/hybrisServer-*.zip; do unzip $z -d ${PLATFORM_PACKAGE};done && rm -Rf /tmp/hybrisServer-*.zip
RUN mv /hybrisServer/conf/Catalina /opt/tomcat/conf/Catalina
