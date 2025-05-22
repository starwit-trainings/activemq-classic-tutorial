#!/bin/bash

## Figure out the ACTIVEMQ_BASE from the directory this script was run from
PRG="$0"
progname=`basename "$0"`
saveddir=`pwd`
# need this for relative symlinks
dirname_prg=`dirname "$PRG"`
cd "$dirname_prg"
while [ -h "$PRG" ] ; do
  ls=`ls -ld "$PRG"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '.*/.*' > /dev/null; then
    PRG="$link"
  else
    PRG=`dirname "$PRG"`"/$link"
  fi
done
ACTIVEMQ_BASE=`dirname "$PRG"`/..
cd "$saveddir"

ACTIVEMQ_BASE=`cd "$ACTIVEMQ_BASE" && pwd`

## Enable remote debugging
#export ACTIVEMQ_DEBUG_OPTS="-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"

## Add system properties for this instance here (if needed), e.g
#export ACTIVEMQ_OPTS_MEMORY="-Xms256M -Xmx1G"
#export ACTIVEMQ_OPTS="$ACTIVEMQ_OPTS_MEMORY -Dorg.apache.activemq.UseDedicatedTaskRunner=true -Djava.util.logging.config.file=logging.properties"

if [[ -n "$ACTIVEMQ_HOME" ]]; then
  echo "ActiveMQ home is set use $ACTIVEMQ_HOME"
else
  export ACTIVEMQ_HOME="../../../../../apache-activemq-6.1.6"
fi

export ACTIVEMQ_BASE=$ACTIVEMQ_BASE

${ACTIVEMQ_HOME}/bin/activemq "$@"