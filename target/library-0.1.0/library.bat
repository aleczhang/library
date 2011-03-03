@echo off
title Library
::set LIBRARY_HOME=%cd%
::set JAVA_HOME=D:\Java\jdk1.6.0_20
::set PATH=%JAVA_HOME%\bin;%PATH%
::set CLASSPATH=%LIBRARY_HOME%\lib\*;%LIBRARY_HOME%\config;%CLASSPATH%
::set DEBUG_OPTION=-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005
::set DEBUG_OPTION=-agentpath:D:\PROGRA~3\JPROFI~1\bin\windows\jprofilerti.dll=port=8849
::start "Library" /B %JAVA_HOME%\bin\java -Dswing.defaultlaf=com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel %DEBUG_OPTION% cn.ox85.ui.Main
::set DEBUG_OPTION=-J-Xdebug -J-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005
::set DEBUG_OPTION=-J-agentpath:D:\PROGRA~3\JPROFI~1\bin\windows\jprofilerti.dll=port=8849
library.exe -console %DEBUG_OPTION%
