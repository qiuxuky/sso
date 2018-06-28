@echo off
echo [INFO] Install cas war to qx repository.

cd /d %~dp0
call mvn clean install deploy -Dmaven.test.skip=true
pause