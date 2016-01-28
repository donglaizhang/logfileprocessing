#!/bin/bash

if [ ! $1 ]; then
	echo "pleas input the directory of log files, like: sh processor.sh /usr/logs"
	exit 1
fi

echo "Directory of log files is " $1
MAINCLASS='org.donglai.logp.Processor'
SERVER_HOME='.'
#CLASSPATH="$SERVER_HOME"
for f in `ls $SERVER_HOME/lib/*.jar`
do
    CLASSPATH="$CLASSPATH:$f"
done
#echo $CLASSPATH
export CLASSPATH
java -Xmx100m $MAINCLASS $1 > rowprocessor.log
