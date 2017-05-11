#!/bin/sh

# This script shall be executed after `ant production` has run.
# Copy files from hybris build dir into the hybris platform.
# Build final hybris docker container.
(

mv hybris/temp/hybris/hybrisServer/*.zip docker/Images/04_platform/hybrisServer/

cd docker/Images/04_platform
./build.sh docker-registry.dc.springernature.pe/sprcom/sprcom.hybris.platform:$1

docker push docker-registry.dc.springernature.pe/sprcom/sprcom.hybris.platform:$1
)
