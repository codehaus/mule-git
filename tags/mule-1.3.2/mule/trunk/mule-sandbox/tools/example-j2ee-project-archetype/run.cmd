SET basedir=%~dp0

call javasun14

cd example-j2ee-project-archetype
call mvn install %*

cd %basedir%\testing
rm -rf MyProject

REM -B is for BATCH processing, omitting it will produce nice user prompts
call ..\project -B -DserviceName=Example -DgenerateWar=false %*