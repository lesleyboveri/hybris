FROM docker-registry.dc.springernature.pe/sprcom/sprcom.hybris.base:latest
MAINTAINER axel.grossmann@hybris.de

# Note: adding a *.tar.gz automatically unzips the archive
ADD apache-tomcat-*.tar.gz /opt/

RUN mv /opt/apache-tomcat* /opt/tomcat
