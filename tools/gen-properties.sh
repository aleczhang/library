#/bin/sh
echo Generate properties...
WS=/export/dev/azhang/library
iconv -f GB2312 -t UTF-8 $WS/tools/resources/Resources_zh_CN.properties | $JAVA_HOME/bin/native2ascii > $WS/src/main/resources/cn/ox85/ui/resources/Resources_zh_CN.properties
