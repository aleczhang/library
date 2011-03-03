#!/bin/sh
LIBRARY_HOME=`pwd`
echo $LIBRARY_HOME
CLASSPATH=$LIBRARY_HOME/lib/*:$LIBRARY_HOME/config:$CLASSPATH:$JAVA_HOME/lib
#DEBUG_OPTION=-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005
echo $CLASSPATH
$JAVA_HOME/bin/java -cp $CLASSPATH -Dawt.useSystemAAFontSettings=on -Dswing.defaultlaf=com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel $DEBUG_OPTION cn.ox85.ui.Main
