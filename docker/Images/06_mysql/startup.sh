#!/bin/sh

docker run -d -e MYSQL_PASSWORD=hybris_user -e MYSQL_USER=hybris_user -p 3306:3306 --name mysql-01 sprcom.hybris.mysql
