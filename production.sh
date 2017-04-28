#!/bin/bash

# Argument Description

# tomcat.legacy.deployment ###
# true - configuration of all hybris web applications is generated into server.xml
# false - configuration of all hybris web applications is generated into separate configuration files

# production.legacy.mode ###
# true - paths inside server*.xml and wrapper*.conf files are not changed
# false - path inside server*.xml and wrapper*.conf files are changed to PLATFORM_HOME related, where PLATFORM_HOME cat be set using an environment variable

# production.include.tomcat ###
# true - production package contains tomcat application server
# false - production package does not contain tomcat application server
 
ant production -Dtomcat.legacy.deployment=false -Dproduction.legacy.mode=false -Dproduction.include.tomcat=false
