@echo off
title Install third-party jars to maven local repository

REM Change to your JAVA_HOME
set JAVA_HOME=d:\Java\jdk1.6.0_20

REM Change to your MAVEN_HOME
set MAVEN_HOME=d:\dev\apache-maven-3.0.1

set PATH=%JAVA_HOME%\bin;%ANT_HOME%\bin;%MAVEN_HOME%\bin;%PATH%
call mvn install:install-file -Dfile=binding-2.0.6.jar -DgroupId=com.jgoodies -DartifactId=binding -Dversion=2.0.6 -Dpackaging=jar
call mvn install:install-file -Dfile=forms-1.3.0.jar -DgroupId=com.jgoodies -DartifactId=forms -Dversion=1.3.0 -Dpackaging=jar
call mvn install:install-file -Dfile=looks-2.3.1.jar -DgroupId=com.jgoodies -DartifactId=looks -Dversion=2.3.1 -Dpackaging=jar
call mvn install:install-file -Dfile=validation-2.1.0.jar -DgroupId=com.jgoodies -DartifactId=validation -Dversion=2.1.0 -Dpackaging=jar
call mvn install:install-file -Dfile=mybatis-3.0.3.jar -DgroupId=mybatis -DartifactId=mybatis -Dversion=3.0.3 -Dpackaging=jar
call mvn install:install-file -Dfile=microba-0.4.4.3.jar -DgroupId=com.michaelbaranov -DartifactId=microba -Dversion=0.4.4.3 -Dpackaging=jar
call mvn install:install-file -Dfile=jxl-2.6.12.jar -DgroupId=jxl -DartifactId=jxl -Dversion=2.6.12 -Dpackaging=jar
call mvn install:install-file -Dfile=slf4j-api-1.6.1.jar -DgroupId=org.slf4j -DartifactId=slf4j-api -Dversion=1.6.1 -Dpackaging=jar
call mvn install:install-file -Dfile=logback-core-0.9.27.jar -DgroupId=ch.qos.logback -DartifactId=logback-core -Dversion=0.9.27 -Dpackaging=jar
call mvn install:install-file -Dfile=logback-classic-0.9.27.jar -DgroupId=ch.qos.logback -DartifactId=logback-classic -Dversion=0.9.27 -Dpackaging=jar
call mvn install:install-file -Dfile=jcommon-1.0.16.jar -DgroupId=org.jfree.common -DartifactId=jcommon -Dversion=1.0.16 -Dpackaging=jar
call mvn install:install-file -Dfile=jfreechart-1.0.13.jar -DgroupId=org.jfree.chart -DartifactId=jfreechart -Dversion=1.0.13 -Dpackaging=jar

echo.
pause
