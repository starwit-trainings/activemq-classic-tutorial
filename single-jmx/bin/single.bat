@echo off
set ACTIVEMQ_HOME="../../../apache-activemq-6.1.6"
set ACTIVEMQ_BASE=".."
set ACTIVEMQ_CONF="../conf"
set ACTIVEMQ_DATA="../data"
set ACTIVEMQ_OPTS="%ACTIVEMQ_OPTS_MEMORY% -javaagent:../jmx_prometheus_javaagent-1.3.0.jar=7878:../single-jmx/config.yaml "

set PARAM=%1
:getParam
shift
if "%1"=="" goto end
set PARAM=%PARAM% %1
goto getParam
:end

%ACTIVEMQ_HOME%/bin/activemq %PARAM%