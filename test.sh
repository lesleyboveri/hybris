#!/bin/bash
(
cd hybris/bin/platform
ant customize all unittests -Dtestclasses.packages=com.springernature.*
ant customize all unittests -Dtestclasses.packages=com.springer.*
ant customize all unittests -Dtestclasses.packages=com.mea.*
)
