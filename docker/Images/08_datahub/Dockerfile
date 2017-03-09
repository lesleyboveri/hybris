FROM sprcom.hybris.tomcat
MAINTAINER fabian.engels@springer.com

# tomcat location for start script
ENV TOMCAT /opt/tomcat

ENV CATALINA_HOME /opt/tomcat

ADD datahub/ /opt/datahub/
ADD datahub-webapp-6.3.0.0-RC2.war /opt/datahub-webapp-6.3.0.0-RC2.war
ADD datahub-webapp.xml ${TOMCAT}/conf/Catalina/localhost/datahub-webapp.xml

EXPOSE 8080

CMD ["/opt/tomcat/bin/startup.sh"]
