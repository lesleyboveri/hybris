FROM mysql:5.6.23

ENV MYSQL_ROOT_PASSWORD=root

ENV MYSQL_USER=hybris
ENV MYSQL_PASSWORD=hybris
ENV MYSQL_DATABASE=hybris

RUN echo "innodb_flush_log_at_trx_commit = 0" >> /etc/mysql/my.cnf && echo "max_connections = 5000" >> /etc/mysql/my.cnf
