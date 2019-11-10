#!/usr/bin/bash
DEBUG="-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5678"
DEBUG=""
JVM_OPTION="-XX:+HeapDumpOnOutOfMemoryError -Xmx1024m -Xms1024m -XX:+UseG1GC"
CUR_DIR=.
JAR_NAME="odataDemo.jar"

echo "JAR_NAME: "$JAR_NAME
echo "CUR_DIR: "$JAR_NAME
echo "CUR_DIR: "${CUR_DIR}/target/${JAR_NAME}

java $JVM_OPTION  $DEBUG \
    -Dspring.profiles.active=dev \
    -Dserver.port=9099 -jar ${CUR_DIR}/target/${JAR_NAME} > ${CUR_DIR}/odataDemo.log 2>&1
