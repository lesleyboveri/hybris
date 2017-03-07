#!/bin/bash
set -e

cd ../../Images/01_base
./build.sh

cd ../../Images/02_tomcat
./build.sh

cd ../../Images/03_server
./build.sh

cd ../../Images/04_platform
./build.sh y.i.accelerator

cd ../../Images/05_solr
./build.sh

cd ../../Images/06_mysql
./build.sh

cd ../../Images/07_apache
./build.sh
