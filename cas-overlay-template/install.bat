@echo off
echo [INFO] Install cas war to local repository.

cd /d %~dp0
call mvn clean install -Dmaven.test.skip=true
pause