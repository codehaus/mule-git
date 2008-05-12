@echo off
setlocal
REM There is no need to call this if you set the MULE_HOME in your environment properties
REM but you must also define MULE_LIB for the example (see below)
REM or specify the config as a file: URI (see README.txt)
if "%MULE_HOME%" == "" SET MULE_HOME=%~dp0..\..
if "%MULE_BASE%" == "" SET MULE_BASE=%MULE_HOME%

REM This extends the classpath to include the configuration directory
REM Any changes to the files in .\conf will take precedence over those deployed to %MULE_HOME%\lib\user
SET MULE_LIB=.\conf
SET JYTHON=jython-2.2.1.jar

if exist "%MULE_BASE%\lib\user\%JYTHON%" goto :run
if exist "%MULE_HOME%\lib\user\%JYTHON%" goto :run
echo This example requires additional libraries which need to be downloaded by the build script.  Please follow the instructions in the README.txt file.
goto :eof

:run
ECHO The Scripting example is available in two flavors:
ECHO   1. Groovy
ECHO   2. Python
SET /P Choice=Select the one you wish to execute and press Enter...
IF '%Choice%'=='1' SET SCRIPTFILE="greedy.groovy"
IF '%Choice%'=='2' SET SCRIPTFILE="greedy.py"
if "%SCRIPTFILE%" == "" goto :run

REM ECHO Welcome to the JSR 223-powered Change Machine.  This machine will give you
REM ECHO change for the amount you request.  The amount of change is cumulative, so you
REM ECHO can keep asking for more and more change until you've had enough.
REM ECHO To get started, select a currency:
REM ECHO   1. U.S. Dollars
REM ECHO   2. U.K. Pounds Sterling
REM SET /P Choice=
REM IF '%Choice%'=='1' SET CURRENCY="USD"
REM IF '%Choice%'=='2' SET CURRENCY="GBP"
REM if "%CURRENCY%" == "" goto :run

call "%MULE_BASE%\bin\mule.bat" -config change-machine.xml -M-Dscriptfile=%SCRIPTFILE% -M-Dcurrency=%CURRENCY%
