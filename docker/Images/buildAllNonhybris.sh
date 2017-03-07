#!/bin/bash
set -e

cd 01_base
./build.sh

cd ../02_tomcat
./build.sh

cd ../03_server
./build.sh

cd ../06_mysql
./build.sh

cd ../07_apache
./build.sh
