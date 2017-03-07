FROM ubuntu:trusty
MAINTAINER axel.grossmann@hybris.de

# Packages
RUN apt-get update && apt-get upgrade -y && apt-get install -y apache2 wget && apt-get clean

# Apache httpd 2.4
RUN a2enmod proxy && \
    a2enmod proxy_http && \
    a2enmod proxy_balancer && \
    a2enmod status && \
    a2enmod lbmethod_byrequests && \
    a2enmod headers && \
    a2enmod rewrite && \
    a2enmod ssl

#ADD conf/ports.conf /etc/apache2/ports.conf
ADD conf/defaultsite.conf /etc/apache2/sites-available/000-default.conf
ADD conf/defaultsite.html /var/www/html/index.html
ADD conf/apache.crt /etc/httpd/certs/apache.crt
ADD conf/apache.key /etc/httpd/certs/apache.key

RUN mkdir -p /etc/apache2/logs/

EXPOSE 8080
EXPOSE 8443

CMD /usr/sbin/apache2ctl -D FOREGROUND
