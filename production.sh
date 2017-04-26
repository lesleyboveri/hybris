#!/bin/bash

ant production -Dproduction.legacy.mode=false -Dproduction.include.tomcat=false -Dtomcat.legacy.deployment=false
