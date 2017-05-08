#!/bin/bash

# This script shall be executed after `ant production` has run.
# Copy files from hybris build dir into the hybris platform.
# Build final hybris docker container.

build_number=""
workspace=""

while getopts "b:w:" opt; do
    case "$opt" in
      b) build_number="$OPTARG";;
      w) workspace="$OPTARG";;
      [?])	echo >&2 "Usage: $0 [-b build_number] [-w workspace]"
		        exit 1;;
    esac
  
done
shift "$(($OPTIND-1))"

if [[ -z "$build_number" ]]; then
  exit 1
fi

mv $workspace/hybris/temp/hybris/hybrisServer/*.zip $workspace/docker/Images/04_platform/hybrisServer/

cd $workspace/docker/Images/04_platform
./build.sh docker-registry.dc.springernature.pe/sprcom/sprcom.hybris.platform:$build_number

docker push docker-registry.dc.springernature.pe/sprcom/sprcom.hybris.platform:$build_number
