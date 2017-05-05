#!/bin/sh 

# This script deletes files not required by SpringerNature from the SAP Hybris Platform archive.
if [ "$1" == "" ]; then
  echo 'Usage : stripHybrisRelease.sh <hybrisPlatform.zip>'
  exit
fi

zip --delete $1 \
hybris-ems/* \
c4c-integration/* \
build-tools/* \
hybris-Mobile-Apps-SDK/* \
hybris/bin/ext-data/* \
hybris-sbg/*
