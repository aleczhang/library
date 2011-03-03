@echo off
d:
title maven 3.0.1 env
set JAVA_HOME=D:\Java\jdk1.6.0_20
set MAVEN_HOME=d:\dev\apache-maven-3.0.1
set PATH=%JAVA_HOME%\bin;%MAVEN_HOME%\bin;%PATH%

echo ==========================================================
echo   JAVA_HOME=%JAVA_HOME%
echo   MAVEN_HOME=%MAVEN_HOME%
echo ==========================================================
cd ..
cmd /k
@echo on
