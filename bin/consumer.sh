#!/bin/bash

CWD=`dirname $0`
DST=$CWD/../build/classes
LIB=$CWD/../build/lib

CP=$LIB/activemq-all-5.9.0.jar:$LIB/commons-cli-1.2.jar:$DST

java -cp $CP StompMessageConsumer $@