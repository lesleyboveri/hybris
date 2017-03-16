# sprcom-hybris
Springer.com Hybris Distribution

## Create Hybris Platform Docker Image

wget HYBRIS.zip
unzip HYBRIS.zip
cp -R hybris/bin/ext-commerce/solrserver ~/github/sprcom-hybris/docker/Images/05_solr/

# DataHub MySQL

mkdir -p /opt/hybris/mysql/lib

docker run -d -v /opt/hybris/mysql/lib:/var/lib/mysql --name dev-hybris-datahub-mysql-01 sprcom.hybris.datahub.mysql

## MYSQL DataHub Schema creation

'''
CREATE SCHEMA `integration`
DEFAULT CHARACTER SET utf8
COLLATE utf8_bin ;
'''

create database integration;

CREATE USER 'hybris'@<host>' IDENTIFIED BY 'hybris';

GRANT ALL PRIVILEGES ON integration.* TO 'hybris'@'<host>'
    ->     WITH GRANT OPTION;

For example, place mysql-connector-java-5.1.x-bin.jar in the <TOMCAT_HOME>/lib directory.




## Build docker container
docker push docker-registry.dc.springernature.pe/sprcom/sprcom.hybris.solr

## Run docker container
docker run -d -p 8983:8983 -v /opt/hybris/solr/data:/opt/solr/server/solr/cores --name dev-hybris-solr-01 docker-registry.dc.springernature.pe/sprcom/sprcom.hybris.solr

## Test docker container
docker exec -itu root dev-hybris-solr-01 bash

## Solr startup

```
mkdir -p /opt/hybris/solr/data
```

sudo docker run \
  -d \
  -v /opt/hybris/solr/data:/opt/solr/server/solr/cores \
  --name y.s.solr \
  y.i.solr
