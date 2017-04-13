#!/bin/bash

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
