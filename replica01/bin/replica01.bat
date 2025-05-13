@echo off
set ACTIVEMQ_HOME="/home/markus/workspaces/trainings/activemq/apache-activemq-6.1.6"
set ACTIVEMQ_BASE="/home/markus/workspaces/trainings/activemq/masterslave/master"

set PARAM=%1
:getParam
shift
if "%1"=="" goto end
set PARAM=%PARAM% %1
goto getParam
:end

%ACTIVEMQ_HOME%/bin/activemq %PARAM%