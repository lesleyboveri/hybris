#!/bin/bash

# This script will download unzip and build a hybris platform using the customized extensions.
wget -N http://artifactory.tools.springer-sbm.com/repo/sprcom-hybris-local/HYBRISCOMM/HYBRISCOMM6300P_0-70002554_small.zip
unzip -o -qq HYBRISCOMM6300P_0-70002554_small.zip

cd ./hybris/bin/platform

export PLATFORM_HOME=`pwd`
export ANT_OPTS="-Xmx512m -Dfile.encoding=UTF-8"
export ANT_HOME="$PLATFORM_HOME/apache-ant-1.9.1"
chmod +x "$ANT_HOME/bin/ant"
chmod +x "$PLATFORM_HOME/license.sh"
if [[ ! "$PATH" =~ "$ANT_HOME" ]]; then
    export PATH=$ANT_HOME/bin:$PATH
fi

ant production -Dproduction.legacy.mode=false -Dproduction.include.tomcat=false -Dtomcat.legacy.deployment=false
