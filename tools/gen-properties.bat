@echo off
title Generate zh_CN properties
set JAVA_HOME=D:\Java\jdk1.6.0_20
set WS=%cd%\..

echo generating...
%JAVA_HOME%\bin\native2ascii.exe %cd%\resources\Resources_zh_CN.properties > %WS%\src\main\resources\cn\ox85\ui\resources\Resources_zh_CN.properties

pause
